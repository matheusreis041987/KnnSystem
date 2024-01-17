package com.knnsystem.api.infrastructure.error;

import com.knnsystem.api.exceptions.ErroAutenticacao;
import com.knnsystem.api.exceptions.RelatorioSemResultadoException;
import com.knnsystem.api.exceptions.UsuarioCadastradoException;
import com.knnsystem.api.exceptions.UsuarioNaoEncontradoException;
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

    @ExceptionHandler({UsuarioCadastradoException.class})
    public ResponseEntity trataErro409(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }

    @ExceptionHandler({
            UsuarioNaoEncontradoException.class,
            RelatorioSemResultadoException.class})
    public ResponseEntity trataErro404(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }


    private record ErroSoComMensagemValidacao(String mensagem){ }



}
