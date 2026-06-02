package com.filipe_bento.agendamento_barbearia.dto.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ServicoRequestDTO(

    @NotBlank(message = "Nome do serviço é obrigatório")
    String nome,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    BigDecimal preco

) {}