package lk.ijse.dep13.backendexpensemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        // Collect field errors from validation annotations in DTO
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", "Validation failed");
        body.put("timestamp", System.currentTimeMillis());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle other exceptions such as invalid JSON format, missing fields, etc.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "error");
        body.put("message", "An unexpected error occurred");
        body.put("timestamp", System.currentTimeMillis());
        body.put("details", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public void handleAppWideException(AppException appException){
        ResponseStatusException resExp;
        if (appException.getErrorCode() == 404){
            resExp = new ResponseStatusException(HttpStatus.NOT_FOUND, appException.getMessage());
        } else{
            resExp = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, appException.getMessage());
        }
        appException.initCause(resExp);
        throw resExp;
    }
}
