package com.yc.Rhythm.exception;

import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.hibernate.ObjectNotFoundException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yc.Rhythm.dto.res.ErrorResponse;



@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not found");
        errorRes.setMessage(String.format("URL %s, Méthode %s inexistante.", ex.getRequestURL(), ex.getHttpMethod()));
        errorRes.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorRes);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getMessage();

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);
        return ResponseEntity.badRequest().body(errorRes);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " Paramètre Manquant où Nom de paramètre invalide ";

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            String fieldName = null;
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                fieldName = node.getName();
            }
            errors.add(fieldName + " : " + constraintViolation.getMessage());
        }

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Erreur de paramétre");
        errorRes.setMessage("Paramètre invalide");
        errorRes.setParameters(errors);

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        String error = ex.getName();

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, error);
        errorRes.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorRes);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> response = new HashMap<>();
        response.put("code", "400");
        response.put("status", "400 BAD_REQUEST");

        // Collect validation errors
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("errors", errors);

        // Add a timestamp (optional)
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalExceptionHundler(Exception ex) {
        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "");
        errorRes.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({SQLGrammarException.class})
    public ResponseEntity<Object> sqlGrammarException(SQLGrammarException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Bad Request ");
        errorRes.setMessage(ex.getLocalizedMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("400", HttpStatus.BAD_REQUEST, "Bad Request ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<Object> dateTimeParseException(ObjectNotFoundException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not Found ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorRes);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> objectNotFoundException(ObjectNotFoundException ex, WebRequest request) {

        ErrorResponse errorRes = new ErrorResponse("404", HttpStatus.NOT_FOUND, "Not Found ");
        errorRes.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRes);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorRes) {
        HttpStatus status = HttpStatus.valueOf(errorRes.getStatus());
        System.out.println("status = " + status);

        return new ResponseEntity<>(errorRes, status);
    }

    @ExceptionHandler({OptimisticLockException.class, StaleObjectStateException.class})
    public ResponseEntity<Object> handleConcurrencyException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The resource was modified by another transaction. Please reload and try again.");
    }




}
