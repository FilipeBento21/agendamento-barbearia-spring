package com.filipe_bento.agendamento_barbearia.dto.cliente;

import com.filipe_bento.agendamento_barbearia.entity.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String telefone,
    String email
) {

    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getEmail()
        );
    }
}