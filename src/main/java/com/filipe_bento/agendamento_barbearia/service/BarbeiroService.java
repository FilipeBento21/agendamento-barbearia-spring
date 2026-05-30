package com.filipe_bento.agendamento_barbearia.service;

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
    public List<Barbeiro> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Barbeiro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Barbeiro> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Barbeiro salvar(Barbeiro barbeiro) {
        return repository.save(barbeiro);
    }
}