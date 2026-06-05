package com.filipe_bento.agendamento_barbearia.exception;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    
    // Campos adicionados para o teste funcionar
    private String field; 

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    // Construtor completo para o Handler
    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Construtor específico para o seu teste (String, String)
    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    @Getter
    @Setter
    public static class FieldMessage {
        private String fieldName;
        private String message;

        public FieldMessage(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }
}