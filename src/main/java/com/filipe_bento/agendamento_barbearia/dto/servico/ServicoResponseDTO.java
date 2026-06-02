package com.filipe_bento.agendamento_barbearia.dto.servico;

import com.filipe_bento.agendamento_barbearia.entity.Servico;
import java.math.BigDecimal;

public record ServicoResponseDTO(
    Long id,
    String nome,
    BigDecimal preco
) {
    public static ServicoResponseDTO fromEntity(Servico servico) {
        return new ServicoResponseDTO(
            servico.getId(),
            servico.getNome(),
            // Converte o Double da Entity de volta para BigDecimal com segurança
            servico.getPreco() != null ? BigDecimal.valueOf(servico.getPreco()) : null
        );
    }
}