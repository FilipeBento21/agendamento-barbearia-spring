package com.filipe_bento.agendamento_barbearia.dto.barbeiro;

import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;

public record BarbeiroResponseDTO(
    Long id,
    String nome,
    String especialidade
) {

    public static BarbeiroResponseDTO fromEntity(Barbeiro barbeiro) {
        return new BarbeiroResponseDTO(
            barbeiro.getId(),
            barbeiro.getNome(),
            barbeiro.getEspecialidade()
        );
    }
}