package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import com.filipe_bento.agendamento_barbearia.entity.Servico;
import com.filipe_bento.agendamento_barbearia.repository.AgendamentoRepository;
import com.filipe_bento.agendamento_barbearia.repository.BarbeiroRepository;
import com.filipe_bento.agendamento_barbearia.repository.ClienteRepository;
import com.filipe_bento.agendamento_barbearia.repository.ServicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; // Usando o padrão do seu colega
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@Valid @RequestBody Agendamento agendamento) {
        
        if (agendamento.getCliente() == null || !clienteRepository.existsById(agendamento.getCliente().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado.");
        }
        
        if (agendamento.getBarbeiro() == null || !barbeiroRepository.existsById(agendamento.getBarbeiro().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Barbeiro não encontrado.");
        }

        Set<Servico> servicosValidados = new HashSet<>();
        for (Servico s : agendamento.getServicos()) {
            Servico servicoBanco = servicoRepository.findById(s.getId()).orElse(null);
            if (servicoBanco == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Serviço com ID " + s.getId() + " não encontrado.");
            }
            servicosValidados.add(servicoBanco);
        }
        
        agendamento.setCliente(clienteRepository.findById(agendamento.getCliente().getId()).get());
        agendamento.setBarbeiro(barbeiroRepository.findById(agendamento.getBarbeiro().getId()).get());
        agendamento.setServicos(servicosValidados);

        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(agendamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return agendamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        if (agendamentoRepository.existsById(id)) {
            agendamentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}