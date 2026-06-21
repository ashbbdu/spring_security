package com.ecommer_admin.admin_ecommerce.common.advice;

import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException (ResourceNotFoundException e) {
        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors()
                .stream().map(message -> message.getDefaultMessage()).toList();
        ApiError apiError = ApiError.builder()
                .message("Input Validation Failed").httpStatus(HttpStatus.BAD_REQUEST)
                .subErrors(errors).build();
        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(
            DataIntegrityViolationException e) {

        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message("Database constraint violation")
                .subErrors(List.of(e.getMostSpecificCause().getMessage()))
                .build();

        return new ResponseEntity<>(apiError , HttpStatus.CONFLICT);
    }

}
