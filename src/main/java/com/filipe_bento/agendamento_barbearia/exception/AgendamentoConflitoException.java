package com.filipe_bento.agendamento_barbearia.exception;

public class AgendamentoConflitoException extends RuntimeException {
    public AgendamentoConflitoException(String mensagem) {
        super(mensagem);
    }
}