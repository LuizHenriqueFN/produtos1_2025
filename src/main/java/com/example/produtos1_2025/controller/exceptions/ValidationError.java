package com.example.produtos1_2025.controller.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ValidationError extends StandartError {
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    public void addFieldError(String field, String message) {
        this.errors.add(new FieldMessage(field, message));
    }
}
