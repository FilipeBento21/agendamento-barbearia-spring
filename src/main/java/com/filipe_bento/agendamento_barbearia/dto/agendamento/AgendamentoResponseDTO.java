package com.filipe_bento.agendamento_barbearia.dto.agendamento;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record AgendamentoResponseDTO(
    Long id,
    LocalDateTime dataHora,
    ClienteResponseDTO cliente,
    BarbeiroResponseDTO barbeiro,
    Set<ServicoResponseDTO> servicos
) {
    public static AgendamentoResponseDTO fromEntity(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
            agendamento.getId(),
            agendamento.getDataHora(),
            ClienteResponseDTO.fromEntity(agendamento.getCliente()),
            BarbeiroResponseDTO.fromEntity(agendamento.getBarbeiro()),
            agendamento.getServicos().stream()
                    .map(ServicoResponseDTO::fromEntity)
                    .collect(Collectors.toSet())
        );
    }
}