package br.caixa.sistemabancario.config;

import br.caixa.sistemabancario.exceptions.ErroPadrao;
import br.caixa.sistemabancario.exceptions.ErroValidacao;
import br.caixa.sistemabancario.exceptions.ValidacaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErroPadrao> validacaoException(ValidacaoException ex, HttpServletRequest request) {

        HttpStatus status = ex.getStatus();
        ErroPadrao err = new ErroPadrao(Instant.now(), status.toString(),   ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacao> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErroValidacao erros = new ErroValidacao(Instant.now(), status.toString(),"Erro na validacao dos dados", request.getRequestURI());

        //Captura os campos que deram erro
        for (FieldError erro  : ex.getBindingResult().getFieldErrors()) {
            erros.addError(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(erros);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> genericException(Exception e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadrao err = new ErroPadrao(Instant.now(), status.toString(),   e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
