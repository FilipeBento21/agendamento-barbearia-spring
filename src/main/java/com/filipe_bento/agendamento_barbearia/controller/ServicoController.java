package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> criarServico(@Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO novoServico = servicoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO dto) {
        return ResponseEntity.ok(servicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}