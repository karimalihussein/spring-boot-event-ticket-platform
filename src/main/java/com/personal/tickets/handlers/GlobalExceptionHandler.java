package com.personal.tickets.handlers;

import com.personal.tickets.dtos.ErrorReponseDto;
import com.personal.tickets.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ✅ Catch any unhandled exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorReponseDto> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("❌ Unhandled exception: {}", ex.getMessage(), ex);
        ErrorReponseDto error = new ErrorReponseDto(
                ex.getMessage(),
                new Date().toString(),
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ✅ Catch specific known exception (example: IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorReponseDto> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("⚠️ Illegal argument: {}", ex.getMessage());
        ErrorReponseDto error = new ErrorReponseDto(
                ex.getMessage(),
                new Date().toString(),
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ✅ Catch resource not found or custom exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorReponseDto> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("🚫 Resource not found: {}", ex.getMessage());
        ErrorReponseDto error = new ErrorReponseDto(
                ex.getMessage(),
                new Date().toString(),
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // ✅ Handle 404 for missing endpoints (API routes)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorReponseDto> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        // Only return JSON errors for API routes
        if (request.getRequestURI().startsWith("/api/")) {
            log.warn("🚫 API endpoint not found: {}", request.getRequestURI());
            ErrorReponseDto error = new ErrorReponseDto(
                    "Endpoint not found: " + request.getRequestURI(),
                    new Date().toString(),
                    request.getRequestURI(),
                    "NoHandlerFoundException",
                    HttpStatus.NOT_FOUND
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // For non-API routes, return a generic error
        // The WebConfig will handle SPA routing for non-API routes
        log.warn("🚫 Handler not found for: {}", request.getRequestURI());
        ErrorReponseDto error = new ErrorReponseDto(
                "Resource not found: " + request.getRequestURI(),
                new Date().toString(),
                request.getRequestURI(),
                "NoHandlerFoundException",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}