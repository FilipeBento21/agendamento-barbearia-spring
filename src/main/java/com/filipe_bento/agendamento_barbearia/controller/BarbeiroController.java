package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import com.filipe_bento.agendamento_barbearia.service.BarbeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barbeiros")
@RequiredArgsConstructor
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    public ResponseEntity<Barbeiro> criar(@Valid @RequestBody Barbeiro barbeiro) {
        Barbeiro novoBarbeiro = barbeiroService.salvar(barbeiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBarbeiro);
    }

    @GetMapping
    public ResponseEntity<List<Barbeiro>> listar() {
        return ResponseEntity.ok(barbeiroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbeiro> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(barbeiroService.buscarPorId(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Barbeiro>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(barbeiroService.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbeiro> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Barbeiro barbeiro) {

        return ResponseEntity.ok(barbeiroService.atualizar(id, barbeiro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        barbeiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}