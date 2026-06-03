package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import com.filipe_bento.agendamento_barbearia.entity.Servico;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.AgendamentoRepository;
import com.filipe_bento.agendamento_barbearia.repository.BarbeiroRepository;
import com.filipe_bento.agendamento_barbearia.repository.ClienteRepository;
import com.filipe_bento.agendamento_barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(AgendamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
        return AgendamentoResponseDTO.fromEntity(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + dto.clienteId()));

        Barbeiro barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + dto.barbeiroId()));

        Set<Servico> servicosValidados = new HashSet<>();
        for (Long servicoId : dto.servicosIds()) {
            Servico servicoBanco = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Serviço com ID " + servicoId + " não encontrado."));
            servicosValidados.add(servicoBanco);
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.dataHora());
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServicos(servicosValidados);

        Agendamento salvo = agendamentoRepository.save(agendamento);
        return AgendamentoResponseDTO.fromEntity(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
        agendamentoRepository.delete(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));

        if (dto.dataHora() != null) {
            agendamento.setDataHora(dto.dataHora());
        }

        if (dto.clienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + dto.clienteId()));
            agendamento.setCliente(cliente);
        }

        if (dto.barbeiroId() != null) {
            Barbeiro barbeiro = barbeiroRepository.findById(dto.barbeiroId())
                    .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + dto.barbeiroId()));
            agendamento.setBarbeiro(barbeiro);
        }

        if (dto.servicosIds() != null && !dto.servicosIds().isEmpty()) {
            Set<Servico> novosServicos = new HashSet<>();
            for (Long servicoId : dto.servicosIds()) {
                Servico s = servicoRepository.findById(servicoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Serviço com ID " + servicoId + " não encontrado."));
                novosServicos.add(s);
            }
            agendamento.setServicos(novosServicos);
        }

        Agendamento atualizado = agendamentoRepository.save(agendamento);
        return AgendamentoResponseDTO.fromEntity(atualizado);
    }
}