package com.knnsystem.api.model.entity;

public enum Cargo {
    FUNCIONARIO {
        @Override
        Perfil getPerfil() {
            return Perfil.SECRETARIA;
        }
    },
    SINDICO {
        @Override
        Perfil getPerfil() {
            return Perfil.ADMINISTRADOR;
        }
    };

    abstract Perfil getPerfil();
}
