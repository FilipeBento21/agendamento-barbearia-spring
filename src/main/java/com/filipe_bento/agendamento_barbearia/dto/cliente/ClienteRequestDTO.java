package com.filipe_bento.agendamento_barbearia.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "Telefone é obrigatório")
    String telefone,

    @Email(message = "Email inválido")
    String email

) {}