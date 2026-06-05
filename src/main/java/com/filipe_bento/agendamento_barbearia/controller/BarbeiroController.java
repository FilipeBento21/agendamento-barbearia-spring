package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.BarbeiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barbeiros")
@RequiredArgsConstructor
@Tag(name = "Barbeiros", description = "Endpoints para gestão de profissionais (barbeiros)")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo barbeiro", description = "Registra um profissional e sua respectiva especialidade")
    public ResponseEntity<BarbeiroResponseDTO> criarBarbeiro(@Valid @RequestBody BarbeiroRequestDTO dto) {
        BarbeiroResponseDTO novoBarbeiro = barbeiroService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBarbeiro);
    }

    @GetMapping
    @Operation(summary = "Listar todos os barbeiros", description = "Retorna uma lista com todos os profissionais cadastrados")
    public ResponseEntity<List<BarbeiroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(barbeiroService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar barbeiro por ID", description = "Retorna os detalhes de um barbeiro baseado no ID informado")
    public ResponseEntity<BarbeiroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(barbeiroService.buscarPorId(id));
    }

    @GetMapping("/busca")
    @Operation(summary = "Buscar barbeiros por nome", description = "Filtra e retorna uma lista de barbeiros que contenham o nome informado como parâmetro")
    public ResponseEntity<List<BarbeiroResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(barbeiroService.buscarPorNome(nome));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um barbeiro", description = "Remove um profissional do sistema")
    public ResponseEntity<Void> deletarBarbeiro(@PathVariable Long id) {
        barbeiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um barbeiro", description = "Atualiza os dados cadastrais ou especialidade de um barbeiro")
    public ResponseEntity<BarbeiroResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BarbeiroRequestDTO dto) {
        return ResponseEntity.ok(barbeiroService.atualizar(id, dto));
    }
}