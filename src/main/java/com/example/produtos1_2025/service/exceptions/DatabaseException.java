package com.example.produtos1_2025.service.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {

    }

    public DatabaseException(String message) {
        super(message);
    }
}
