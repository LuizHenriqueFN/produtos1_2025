package com.example.produtos1_2025.controller.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class StandartError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}