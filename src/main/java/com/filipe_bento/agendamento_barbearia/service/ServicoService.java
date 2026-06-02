package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Servico;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository repository;

    @Transactional(readOnly = true)
    public List<ServicoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ServicoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com o ID: " + id));
        return ServicoResponseDTO.fromEntity(servico);
    }

    @Transactional
    public ServicoResponseDTO salvar(ServicoRequestDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.nome());
        
        // Conversão de BigDecimal para Double
        if (dto.preco() != null) {
            servico.setPreco(dto.preco().doubleValue());
        }
        
        Servico salvo = repository.save(servico);
        return ServicoResponseDTO.fromEntity(salvo);
    }

    @Transactional
    public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO dto) {
        Servico servicoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com o ID: " + id));
        
        if (dto.nome() != null) {
            servicoExistente.setNome(dto.nome());
        }
        
        // Conversão de BigDecimal para Double
        if (dto.preco() != null) {
            servicoExistente.setPreco(dto.preco().doubleValue());
        }
        
        Servico atualizado = repository.save(servicoExistente);
        return ServicoResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com o ID: " + id));
        repository.delete(servico);
    }
}