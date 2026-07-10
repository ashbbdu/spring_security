package com.ecommer_admin.admin_ecommerce.common.advice;

import com.ecommer_admin.admin_ecommerce.common.exception.BadRequestException;
import com.ecommer_admin.admin_ecommerce.common.exception.ConflictException;
import com.ecommer_admin.admin_ecommerce.common.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiError> handleResourceNotFoundException (ResourceNotFoundException e) {
//        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
//        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
//        List<String> errors = e.getAllErrors()
//                .stream().map(message -> message.getDefaultMessage()).toList();
//        ApiError apiError = ApiError.builder()
//                .message("Input Validation Failed").httpStatus(HttpStatus.BAD_REQUEST)
//                .subErrors(errors).build();
//        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
//    }
//
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException (ResourceNotFoundException e) {
        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
//        return new ResponseEntity<>(apiError , HttpStatus.BAD_REQUEST);
        return handleGlobalResponse(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        List<String> errors = e.getAllErrors()
                .stream().map(message -> message.getDefaultMessage()).toList();
        ApiError apiError = ApiError.builder()
                .message("Input Validation Failed").httpStatus(HttpStatus.BAD_REQUEST)
                .subErrors(errors).build();
        return handleGlobalResponse(apiError , HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(
//            DataIntegrityViolationException e) {
//
//        ApiError apiError = ApiError.builder()
//                .httpStatus(HttpStatus.CONFLICT)
//                .message("Database constraint violation")
//                .subErrors(List.of(e.getMostSpecificCause().getMessage()))
//                .build();
//
//        return handleGlobalResponse(apiError , HttpStatus.CONFLICT);
//    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleConflictFieldException (ConflictException e) {
        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.CONFLICT).message(e.getMessage()).build();
        return handleGlobalResponse(apiError , HttpStatus.CONFLICT);
    }

    public ResponseEntity<ApiResponse<?>> handleGlobalResponse (ApiError apiError , HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , status);
    }



//    for Request Params
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingRequestParamsException (MissingServletRequestParameterException e) {
        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.BAD_REQUEST).message(e.getParameterName()).build();
        return handleGlobalResponse(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingRequestParamsException (MethodArgumentTypeMismatchException e) {
        ApiError apiError = ApiError.builder().httpStatus(HttpStatus.BAD_REQUEST).message(e.getName() +
                " should be of type " +
                e.getRequiredType().getSimpleName()).build();
        return handleGlobalResponse(apiError , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            HandlerMethodValidationException ex) {

        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .subErrors(List.of(ex.getMessage()))
                .build();

        return handleGlobalResponse(apiError, HttpStatus.BAD_REQUEST);
    }


//    on the controller when adding validation on @Positive
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        System.out.println("handled by ConstraintViolationException");
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return handleGlobalResponse(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(
            BadRequestException ex) {
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build();

        return handleGlobalResponse(apiError, HttpStatus.BAD_REQUEST);
    }
}
