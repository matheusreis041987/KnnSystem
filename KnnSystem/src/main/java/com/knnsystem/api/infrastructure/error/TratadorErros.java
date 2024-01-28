package com.knnsystem.api.infrastructure.error;

import com.knnsystem.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(
            {EntidadeCadastradaException.class,
            ErroComunicacaoComBancoException.class})
    public ResponseEntity trataErro409(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }

    @ExceptionHandler({IllegalArgumentException.class, RegraNegocioException.class})
    public ResponseEntity trataErro400ComMensagem(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }

    @ExceptionHandler({
            EntidadeNaoEncontradaException.class,
            RelatorioSemResultadoException.class})
    public ResponseEntity trataErro404(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErroSoComMensagemValidacao(exception.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros = exception.getFieldErrors();
        for (FieldError erro: erros) {
            return ResponseEntity.badRequest().body(
                    new ErroSoComMensagemValidacao(erro.getDefaultMessage())
                );
        }
        return ResponseEntity.badRequest().build();
    }

    private record ErroSoComMensagemValidacao(String mensagem){ }



}
