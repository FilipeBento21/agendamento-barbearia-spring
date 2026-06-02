package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@Valid @RequestBody AgendamentoRequestDTO dto) {
        AgendamentoResponseDTO novoAgendamento = agendamentoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }
}