package com.filipe_bento.agendamento_barbearia.exception;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    private final List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}