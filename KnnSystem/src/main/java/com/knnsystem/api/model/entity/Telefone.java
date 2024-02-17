package com.knnsystem.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "telefone", schema = "sch_pessoas" )
@Getter
@Setter
public class Telefone {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numero")
    @Size(max = 15)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "fk_id_pessoa", nullable = false)
    private Pessoa pessoa;

    @Override
    public String toString() {
        return this.numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telefone telefone = (Telefone) o;

        return numero.equals(telefone.numero);
    }

    @Override
    public int hashCode() {
        return numero.hashCode();
    }
}
