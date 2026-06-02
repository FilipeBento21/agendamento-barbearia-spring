package com.filipe_bento.agendamento_barbearia.dto.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public record AgendamentoRequestDTO(

    @NotNull(message = "Data e hora são obrigatórias")
    @Future(message = "O agendamento deve ser em uma data futura")
    LocalDateTime dataHora,

    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "ID do barbeiro é obrigatório")
    Long barbeiroId,

    @NotEmpty(message = "O agendamento deve conter pelo menos um serviço")
    Set<Long> servicosIds

) {}