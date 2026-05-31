package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.entity.Servico;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository repository;

    @Transactional(readOnly = true)
    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Servico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado com o ID: " + id));
    }

    @Transactional
    public Servico salvar(Servico servico) {
        return repository.save(servico);
    }

    @Transactional
    public Servico atualizar(Long id, Servico dadosAtualizados) {
        Servico servicoExistente = buscarPorId(id);
        servicoExistente.setNome(dadosAtualizados.getNome());
        servicoExistente.setPreco(dadosAtualizados.getPreco());
        return repository.save(servicoExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = buscarPorId(id);
        repository.delete(servico);
    }
}