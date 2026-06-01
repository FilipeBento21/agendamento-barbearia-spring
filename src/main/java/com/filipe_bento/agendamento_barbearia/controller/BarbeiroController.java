package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
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
    public ResponseEntity<BarbeiroResponseDTO> criar(
            @Valid @RequestBody BarbeiroRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(barbeiroService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<BarbeiroResponseDTO>> listar() {
        return ResponseEntity.ok(barbeiroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(barbeiroService.buscarPorId(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<BarbeiroResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(barbeiroService.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarbeiroResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BarbeiroRequestDTO dto) {

        return ResponseEntity.ok(barbeiroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        barbeiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}