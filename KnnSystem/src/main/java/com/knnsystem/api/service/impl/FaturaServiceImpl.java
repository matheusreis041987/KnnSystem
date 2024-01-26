package com.knnsystem.api.service.impl;


import com.knnsystem.api.dto.FaturaCadastroDTO;
import com.knnsystem.api.dto.FaturaResultadoDTO;
import com.knnsystem.api.dto.ResultadoPagamentoDTO;
import com.knnsystem.api.exceptions.EntidadeCadastradaException;
import com.knnsystem.api.exceptions.EntidadeNaoEncontradaException;
import com.knnsystem.api.exceptions.RegraNegocioException;
import com.knnsystem.api.infrastructure.api.financeiro.ApiInsituicaoFinanceiraService;
import com.knnsystem.api.model.entity.*;
import com.knnsystem.api.model.repository.ContratoRepository;
import com.knnsystem.api.model.repository.FornecedorRepository;
import com.knnsystem.api.model.repository.PagamentoRepository;
import com.knnsystem.api.service.UsuarioService;
import com.knnsystem.api.utils.CalculadoraDiasUteis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.knnsystem.api.model.repository.FaturaRepository;
import com.knnsystem.api.service.FaturaService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class FaturaServiceImpl implements FaturaService  {

	private final BigDecimal LIMIAR_VALOR_APROVACAO_SINDICO = new BigDecimal("30000.00");

	private final BigDecimal LIMIAR_VALOR_JANELA_PAGAMENTO = new BigDecimal("10000.00");

	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private FaturaRepository faturaRepository;

	@Autowired
	private ApiInsituicaoFinanceiraService apiInsituicaoFinanceiraService;

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FornecedorRepository fornecedorRepository;


	@Override
	@Transactional
	public ResultadoPagamentoDTO salvar(FaturaCadastroDTO dto) {

		if (faturaRepository.findByNumero(dto.numeroFatura()).isPresent()) {
			throw new EntidadeCadastradaException("Erro - já existe fatura cadastrada para esses dados");
		}

		validaRegrasNegocioDeDatas(dto);

		var dadosParaInstituicaoFinanceira = dto.getDadosPagamentos();
		var contratoOptional = contratoRepository.findByNumContrato(dto.numeroContrato());
		if (contratoOptional.isEmpty()){
			throw new EntidadeNaoEncontradaException("Não há contrato para o número informado");
		}

		// salva fatura e pagamento
		Pagamento pagamento = PagamentoFactory.createPagamento(
				dto.domicilioBancario());
		pagamento.setDataPagamento(dto.dataPagamento());
		pagamento.setContrato(contratoOptional.get());
		pagamento.setUsuario(usuarioService.getUsuarioLogado());
		pagamento.setValorPagamento(dto.valor());
		pagamento.setPercJuros(dto.percentualJuros());
		pagamento.efetuarPagamento();

		Fatura fatura = new Fatura();
		fatura.setNumero(dto.numeroFatura());
		fatura.setValor(dto.valor());
		fatura.setVencimento(dto.dataPagamento());


		// validação a partir de 30 mil, entre 10 e 30 dias
		if (isNecessariaAprovacaoSindico(dto)){
			pagamento.setTemAprovacao(true);
			pagamento.setStatusPagamento(StatusPagamento.AGUARDANDO_APROVACAO);
			fatura.setStatusPagamento(StatusPagamento.AGUARDANDO_APROVACAO);
			faturaRepository.save(fatura);
			pagamentoRepository.save(pagamento);
			return new ResultadoPagamentoDTO(
					StatusPagamento.AGUARDANDO_APROVACAO
			);
		}
		pagamento.setTemAprovacao(false);
		pagamento.setStatusPagamento(StatusPagamento.ENVIADO_PARA_PAGAMENTO);
		fatura.setStatusPagamento(StatusPagamento.ENVIADO_PARA_PAGAMENTO);
		faturaRepository.save(fatura);
		pagamentoRepository.save(pagamento);


		return apiInsituicaoFinanceiraService
				.efetuarPagamento(dadosParaInstituicaoFinanceira);
	}

	@Override
	@Transactional
	public List<FaturaResultadoDTO> listar(String cnpjFornecedor, String razaoSocial, String numeroContrato, Long numeroFatura) {

		// TODO: Solução mais escalável que a atual

		List<Fornecedor> fornecedores = fornecedorRepository.findByCnpjOrRazaoSocialOrNumControle(cnpjFornecedor, razaoSocial, null);
		Optional<Fatura> faturaOptional = faturaRepository.findByNumero(numeroFatura);
		Optional<Contrato> contratoOptional = contratoRepository.findByNumContrato(numeroContrato);

		List<FaturaResultadoDTO> resultado = new ArrayList<>();

		if (faturaOptional.isPresent()){
			var fatura = faturaOptional.get();
			resultado.add(new FaturaResultadoDTO(
					fatura.getPagamento().getContrato().getNumContrato(),
					fatura.getNumero(),
					fatura.getPagamento().getContrato().getFornecedor().getCnpj(),
					fatura.getPagamento().getContrato().getFornecedor().getRazaoSocial(),
					fatura.getValor(),
					fatura.getVencimento()
					));
		}

		for (Fornecedor fornecedor: fornecedores){
			var contratos = contratoRepository.findAllByFornecedor(fornecedor);
            contratoOptional.ifPresent(contratos::add);
			for (Contrato contrato: contratos){
				var pagamentos = pagamentoRepository.findAllByContrato(contrato);
				for (Pagamento pagamento: pagamentos) {
					faturaOptional = faturaRepository.findByPagamento(pagamento);
					faturaOptional.ifPresent(fatura -> resultado.add(
                            new FaturaResultadoDTO(
                                    contrato.getNumContrato(),
                                    fatura.getNumero(),
                                    fornecedor.getCnpj(),
                                    fornecedor.getRazaoSocial(),
                                    fatura.getValor(),
                                    fatura.getVencimento()
                            )
                    ));

				}
			}

		}


		return resultado;
	}

	@Override
	@Transactional
	public void inativar(Long id) {
		var fatura = obtemFaturaPorId(id);
		fatura.setStatusPagamento(StatusPagamento.INATIVO);
	}

	private void validaRegrasNegocioDeDatas(FaturaCadastroDTO dto){
		// validação vencimento futuro
		if (!isNoFuturo(dto)){
			throw new RegraNegocioException("Erro - favor escolher uma data futura");
		}
		// validação mesmo mês
		if (!isNoMesmoMes(dto)){
			throw new RegraNegocioException("Erro - favor escolher uma data futura do mês corrente");
		}
		// validação dados dias úteis
		if (!isAposPrazoMinimo(dto)){
			throw new RegraNegocioException("Não pode haver pagamento com menos de 5 dias úteis");
		}

		// validação a partir de 10 mil, entre 10 e 30 dias
		if (!isNaJanelaDePagamento(dto)){
			throw new RegraNegocioException("Erro - Pagamentos acima de R$ 10 mil devem ter vencimento entre 10 e 30 dias corridos da data atual");
		}
	}

	private boolean isNecessariaAprovacaoSindico(FaturaCadastroDTO dto){
		return dto.valor().compareTo(LIMIAR_VALOR_APROVACAO_SINDICO) > 0;
	}

	private boolean isNaJanelaDePagamento(FaturaCadastroDTO dto) {
		if (dto.valor().compareTo(LIMIAR_VALOR_JANELA_PAGAMENTO) <= 0) {
			return true;
		} else {
			return DAYS.between(dto.getDataCadastro(), dto.dataPagamento()) >= 10 &&
					DAYS.between(dto.getDataCadastro(), dto.dataPagamento()) <= 30;
		}
	}

	private boolean isAposPrazoMinimo(FaturaCadastroDTO dto) {
		var diasUteisParaVencimento = CalculadoraDiasUteis.calculaDiasUteisEntre(
				dto.getDataCadastro(),
				dto.dataPagamento()
		);
		return diasUteisParaVencimento >= 5;
	}

	private boolean isNoMesmoMes(FaturaCadastroDTO dto){
		return dto.dataPagamento().getMonth().equals(dto.getDataCadastro().getMonth()) &&
				dto.dataPagamento().getYear() == dto.getDataCadastro().getYear();
	}

	private boolean isNoFuturo(FaturaCadastroDTO dto) {
		return  dto.dataPagamento().isAfter(dto.getDataCadastro()) ||
				dto.dataPagamento().isEqual(dto.getDataCadastro());
	}

	private Fatura obtemFaturaPorId(Long id) {
		Optional<Fatura> faturaOptional = faturaRepository.findById(id);
		if (faturaOptional.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Fatura não encontrada");
		}
		return faturaOptional.get();
	}
}
