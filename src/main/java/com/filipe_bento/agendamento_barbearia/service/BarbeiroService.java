package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.BarbeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarbeiroService {

    private final BarbeiroRepository repository;

    @Transactional(readOnly = true)
    public List<BarbeiroResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(BarbeiroResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public BarbeiroResponseDTO buscarPorId(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Barbeiro não encontrado com o ID: " + id));

        return BarbeiroResponseDTO.fromEntity(barbeiro);
    }

    @Transactional(readOnly = true)
    public List<BarbeiroResponseDTO> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(BarbeiroResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public BarbeiroResponseDTO salvar(BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome(dto.nome());

        Barbeiro salvo = repository.save(barbeiro);

        return BarbeiroResponseDTO.fromEntity(salvo);
    }

    @Transactional
    public BarbeiroResponseDTO atualizar(Long id, BarbeiroRequestDTO dto) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Barbeiro não encontrado com o ID: " + id));

        if (dto.nome() != null) {
            barbeiro.setNome(dto.nome());
        }

        Barbeiro atualizado = repository.save(barbeiro);

        return BarbeiroResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Barbeiro barbeiro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Barbeiro não encontrado com o ID: " + id));

        repository.delete(barbeiro);
    }
}