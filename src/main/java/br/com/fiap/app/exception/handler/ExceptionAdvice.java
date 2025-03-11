package br.com.fiap.app.exception.handler;

import br.com.fiap.app.exception.BadRequestException;
import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.app.exception.ForbiddenException;
import br.com.fiap.webui.dtos.response.ErrorField;
import br.com.fiap.webui.dtos.response.ErrorResponse;
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
        return handleException(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleApiException(WebRequest request, BusinessException ex) {
        return handleException(request, ex.getStatus(), ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(WebRequest request, BadRequestException ex) {
        return handleException(request, ex.getStatus(), ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> ForbiddenException(WebRequest request, ForbiddenException ex) {
        return handleException(request, ex.getStatus(), ex);
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

    private ResponseEntity<ErrorResponse> handleException(WebRequest request, HttpStatus httpStatus, Exception ex) {
        log.error("Error", ex);
        String message = ex.getMessage();
        String uri = request.getDescription(false);
        return new ResponseEntity<>(ErrorResponse.builder()
                .path(uri != null ? uri.substring(4) : null)
                .message(message)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .httpCode(httpStatus.value())
                .httpDescription(httpStatus.getReasonPhrase())
                .build(), httpStatus);
    }
}
