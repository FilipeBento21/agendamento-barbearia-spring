package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
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

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;

    @Transactional(readOnly = true)
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
    }

    @Transactional
    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getCliente() == null || !clienteRepository.existsById(agendamento.getCliente().getId())) {
            throw new ResourceNotFoundException("Cliente não encontrado.");
        }

        if (agendamento.getBarbeiro() == null || !barbeiroRepository.existsById(agendamento.getBarbeiro().getId())) {
            throw new ResourceNotFoundException("Barbeiro não encontrado.");
        }

        Set<Servico> servicosValidados = new HashSet<>();
        for (Servico s : agendamento.getServicos()) {
            Servico servicoBanco = servicoRepository.findById(s.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Serviço com ID " + s.getId() + " não encontrado."));
            servicosValidados.add(servicoBanco);
        }

        agendamento.setCliente(clienteRepository.findById(agendamento.getCliente().getId()).get());
        agendamento.setBarbeiro(barbeiroRepository.findById(agendamento.getBarbeiro().getId()).get());
        agendamento.setServicos(servicosValidados);

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void deletar(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamentoRepository.delete(agendamento);
    }
}