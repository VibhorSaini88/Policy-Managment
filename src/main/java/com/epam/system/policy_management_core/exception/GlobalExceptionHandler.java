package com.epam.system.policy_management_core.exception;

import com.epam.system.policy_management_core.component.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException mae, WebRequest webRequest){
        List<String> errors = mae.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String path = webRequest.getDescription(false).replace("uri=","");
        ApiResponse response = ApiResponse.builder()
                .message("Method Argument Not Valid")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now().toString())
                .errors(errors)
                .path(path)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

      @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleArgument(IllegalArgumentException iae, WebRequest request) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiResponse response = ApiResponse.builder()
                .message("Illegal Argument")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now().toString())
                .errors(Collections.singletonList(iae.getMessage()))
                .path(path)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // For 500 - Internal Server Error (e.g., NullPointerException, Database failures)
    @ExceptionHandler(Exception.class) // Catches all unhandled exceptions
    public ResponseEntity<ApiResponse> handleInternalServerError(Exception ex, WebRequest webRequest) {
        String path = webRequest.getDescription(false).replace("uri=", "");
        int status_code = 0;
        HttpStatus httpStatus = null;
        if(ex instanceof ResourceAccessException || ex instanceof HttpClientErrorException){
            status_code = HttpStatus.NOT_FOUND.value();
            httpStatus = HttpStatus.NOT_FOUND;
        }else {
            status_code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ApiResponse response = ApiResponse.builder()
                .message("Failed")
                .statusCode(status_code)
                .timestamp(Instant.now().toString())
                .errors(Collections.singletonList(ex.getMessage())) // Single error message
                .path(path)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

    // Handles 4xx errors (client-side)
    @ExceptionHandler({
            ConstraintViolationException.class,     // @RequestParam/@PathVariable validation (400)
            ResponseStatusException.class,
            MissingFormatArgumentException.class// Custom @ResponseStatus (e.g., 404)
    })
    public ResponseEntity<ApiResponse> handleClientErrors(Exception ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Default

        // Override status if exception has a predefined HTTP status (e.g., 404)
        if (ex instanceof ResponseStatusException) {
            status = (HttpStatus) ((ResponseStatusException) ex).getStatusCode();
        }

        // Extract error messages
        List<String> errors= List.of(ex.getMessage());

        ApiResponse response = ApiResponse.builder()
                .message("Client Error")
                .statusCode(status.value())
                .timestamp(Instant.now().toString())
                .errors(errors)
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(status).body(response);
    }

}
