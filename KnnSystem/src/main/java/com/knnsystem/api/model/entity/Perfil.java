package com.knnsystem.api.model.entity;

public enum Perfil {
    ADMINISTRADOR, SINDICO, SECRETARIA ;

    public String getPapel() {
        return "ROLE_" + this.name();
    };
}
