package com.knnsystem.api.model.entity;

public enum Cargo {
    FUNCIONARIO {
        @Override
        Perfil getPerfil() {
            return Perfil.SECRETARIA;
        }
    },
    ADMINISTRADOR {
        @Override
        Perfil getPerfil() {
            return Perfil.ADMINISTRADOR;
        }
    },
    SINDICO {
        @Override
        Perfil getPerfil() {
            return Perfil.SINDICO;
        }
    };

    abstract Perfil getPerfil();
}
