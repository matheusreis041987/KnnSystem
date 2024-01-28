package com.knnsystem.api.model.repository;

import com.knnsystem.api.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import com.knnsystem.api.model.entity.Fatura;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {

    Optional<Fatura> findByNumero(Long numero);

    Optional<Fatura> findByPagamento(Pagamento pagamento);
}
