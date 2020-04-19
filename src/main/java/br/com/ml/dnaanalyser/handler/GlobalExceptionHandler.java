package br.com.ml.dnaanalyser.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ml.dnaanalyser.exception.ValidationException;


@ControllerAdvice
@SuppressWarnings("rawtypes")
public class GlobalExceptionHandler {

    final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ValidationException.class)
    public ResponseEntity handleBusinessException(HttpServletRequest request, ValidationException ex){
        log.error(String.format("Erro %s", ex.getMessage()));
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
