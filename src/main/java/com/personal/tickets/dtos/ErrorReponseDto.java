package com.personal.tickets.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorReponseDto {
    private String message;
    private String timestamp;
    private String path;
    private String exception;
    private HttpStatus status;
}