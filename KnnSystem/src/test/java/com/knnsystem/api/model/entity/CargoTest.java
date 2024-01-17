package com.knnsystem.api.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CargoTest {

    @DisplayName("Testa perfil para cargo de administrador é de administrador")
    @Test
    void administradorDevePossuirPerfilDeAdministrador(){
        // Act
        Perfil perfil = Cargo.ADMINISTRADOR.getPerfil();
        // Assert
        assertEquals(perfil, Perfil.ADMINISTRADOR);
    }

    @DisplayName("Testa perfil para cargo de funcionário da secretária é de secretaria")
    @Test
    void funcionarioCondominioDevePossuirPerfilDeSecretaria(){
        // Act
        Perfil perfil = Cargo.FUNCIONARIO.getPerfil();
        // Assert
        assertEquals(perfil, Perfil.SECRETARIA);
    }

    @DisplayName("Testa perfil para cargo de síndico é de síndico")
    @Test
    void sindicoDevePossuirPerfilDeSindico(){
        // Act
        Perfil perfil = Cargo.SINDICO.getPerfil();
        // Assert
        assertEquals(perfil, Perfil.SINDICO);
    }

}