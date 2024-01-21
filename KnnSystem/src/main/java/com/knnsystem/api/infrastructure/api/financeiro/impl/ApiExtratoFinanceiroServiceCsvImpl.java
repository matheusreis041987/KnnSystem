package com.knnsystem.api.infrastructure.api.financeiro.impl;

import com.knnsystem.api.dto.ExtratoFinanceiroDTO;
import com.knnsystem.api.infrastructure.api.financeiro.ApiExtratoFinanceiroService;
import com.knnsystem.api.model.entity.ExtratoFinanceiro;
import com.knnsystem.api.model.entity.TipoTransacaoFinanceira;
import com.knnsystem.api.model.entity.TransacaoFinanceira;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ApiExtratoFinanceiroServiceCsvImpl implements ApiExtratoFinanceiroService {

    @Autowired
    private ResourceLoader resourceLoader;

    private final String COMMA_DELIMITER = ";";

    @Override
    public ExtratoFinanceiroDTO gerar(LocalDate dataInicio, LocalDate dataFim) {

        List<TransacaoFinanceira> transacoes = new ArrayList<>();
        ExtratoFinanceiro extrato;

        final Resource fileResource = resourceLoader.getResource("classpath:extrato-financeiro.csv");
        try (Scanner scanner = new Scanner(fileResource.getFile())) {
            List<String> valores = getRecordFromLine(scanner.nextLine());
            extrato = new ExtratoFinanceiro(
                    dataInicio,
                    dataFim,
                    new BigDecimal(valores.get(1))
            );
            while (scanner.hasNextLine()) {
                valores = getRecordFromLine(scanner.nextLine());
                TransacaoFinanceira transacaoFinanceira = new TransacaoFinanceira(
                        TipoTransacaoFinanceira.valueOf(valores.get(0)),
                        LocalDate.parse(valores.get(1)),
                        new BigDecimal(valores.get(3)),
                        valores.get(2)
                );
                try {
                    extrato.adiciona(transacaoFinanceira);
                } catch (IllegalArgumentException ex) {
                    continue;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ExtratoFinanceiroDTO(extrato);
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
