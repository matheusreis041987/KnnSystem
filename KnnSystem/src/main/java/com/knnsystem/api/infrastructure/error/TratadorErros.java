package com.knnsystem.api.infrastructure.error;

import com.knnsystem.api.exceptions.ErroAutenticacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {

    @ExceptionHandler({ErroAutenticacao.class})
    public ResponseEntity trataErro403(Exception exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }

    private record ErroSoComMensagemValidacao(String mensagem){ }



}
