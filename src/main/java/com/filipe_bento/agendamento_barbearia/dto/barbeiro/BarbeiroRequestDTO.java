package com.filipe_bento.agendamento_barbearia.dto.barbeiro;

import jakarta.validation.constraints.NotBlank;

public record BarbeiroRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    String nome

) {}