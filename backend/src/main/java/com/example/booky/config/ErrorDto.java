package com.example.booky.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ErrorDto {
    private String code;
    private String message;
}