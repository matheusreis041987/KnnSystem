package com.knnsystem.api.model.entity;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class ParticipanteContrato {

    @Id
    private String cpf;

    private String nome;

    private String email;


}
