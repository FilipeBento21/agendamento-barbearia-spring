package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.entity.Servico;
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
    public ResponseEntity<Servico> criarServico(@Valid @RequestBody Servico servico) {
        Servico novoServico = servicoService.salvar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @Valid @RequestBody Servico servicoAtualizado) {
        return ResponseEntity.ok(servicoService.atualizar(id, servicoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}