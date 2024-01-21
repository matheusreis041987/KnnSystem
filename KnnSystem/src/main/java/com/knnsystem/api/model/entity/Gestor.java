package com.knnsystem.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gestor", schema = "sch_contratos")
public class Gestor extends ParticipanteContrato {
    @Id
    private String cpf;
}
