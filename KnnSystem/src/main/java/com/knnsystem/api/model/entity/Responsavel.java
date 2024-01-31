package com.knnsystem.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@Table(name = "responsavel", schema = "sch_contratos")
public class Responsavel {
    @Id
    @CPF
    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefone", nullable = false)
    private String telefone;

}
