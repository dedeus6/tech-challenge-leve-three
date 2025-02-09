package br.com.fiap.challenge.diner.adapter.driver.exception.handler;

import br.com.fiap.challenge.diner.adapter.driver.exception.BusinessException;
import br.com.fiap.challenge.diner.adapter.driver.response.ErrorField;
import br.com.fiap.challenge.diner.adapter.driver.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleApiException(WebRequest request, Exception ex) {
        log.error("Error", ex);
        String message = ex.getMessage();
        String uri = request.getDescription(false);
        return new ResponseEntity<>(ErrorResponse.builder()
                .path(uri != null ? uri.substring(4) : null)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleApiException(WebRequest request, BusinessException ex) {
        log.error("Error", ex);
        String message = ex.getMessage();
        String uri = request.getDescription(false);
        return new ResponseEntity<>(ErrorResponse.builder()
                .path(uri != null ? uri.substring(4) : null)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .httpCode(ex.getStatus().value())
                .httpDescription(ex.getStatus().getReasonPhrase())
                .build(), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(WebRequest request, MethodArgumentNotValidException ex) {
        log.error("Error", ex);

        String uri = request.getDescription(false);
        List<ErrorField> errorList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            var error = ErrorField.builder()
                    .field(e.getField())
                    .message(e.getDefaultMessage())
                    .build();
            errorList.add(error);
        });

        return new ResponseEntity<>(ErrorResponse.builder()
                .path(uri != null ? uri.substring(4) : null)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .message("Erro na validação de campo")
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpDescription(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .fields(!errorList.isEmpty() ? errorList : null)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(WebRequest request, ConstraintViolationException ex) {
        log.error("Error", ex);

        String uri = request.getDescription(false);
        List<ErrorField> errorList = new ArrayList<>();
        ex.getConstraintViolations().stream().forEach(e -> {
            var error = ErrorField.builder()
                    .field(String.valueOf(e.getPropertyPath().toString().split("\\.")[1]))
                    .message(e.getMessage())
                    .build();
            errorList.add(error);
        });

        return new ResponseEntity<>(ErrorResponse.builder()
                .path(uri != null ? uri.substring(4) : null)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .message("Erro na validação de campo")
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpDescription(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .fields(!errorList.isEmpty() ? errorList : null)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
