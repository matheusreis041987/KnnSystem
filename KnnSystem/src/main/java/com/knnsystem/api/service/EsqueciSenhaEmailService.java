package com.knnsystem.api.service;

import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;

public interface EsqueciSenhaEmailService {
    void enviarLinkSenhaProvisoria(@Email String email, String senhaProvisoria);
}
