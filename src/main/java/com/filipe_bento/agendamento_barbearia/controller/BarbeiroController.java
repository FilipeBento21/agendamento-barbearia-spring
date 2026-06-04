package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.BarbeiroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barbeiros")
@RequiredArgsConstructor
@Tag(name = "Barbeiros", description = "Gerenciamento de barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    @PostMapping
    @Operation(summary = "Criar barbeiro", description = "Cadastra um novo barbeiro no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Barbeiro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<BarbeiroResponseDTO> criar(
            @Valid @RequestBody BarbeiroRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(barbeiroService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar barbeiros", description = "Retorna todos os barbeiros cadastrados")
    public ResponseEntity<List<BarbeiroResponseDTO>> listar() {
        return ResponseEntity.ok(barbeiroService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar barbeiro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Barbeiro encontrado"),
        @ApiResponse(responseCode = "404", description = "Barbeiro não encontrado")
    })
    public ResponseEntity<BarbeiroResponseDTO> buscar(
            @Parameter(description = "ID do barbeiro")
            @PathVariable Long id) {

        return ResponseEntity.ok(barbeiroService.buscarPorId(id));
    }

    @GetMapping("/busca")
    @Operation(summary = "Buscar barbeiro por nome")
    public ResponseEntity<List<BarbeiroResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome do barbeiro para busca")
            @RequestParam String nome) {

        return ResponseEntity.ok(barbeiroService.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar barbeiro", description = "Atualiza os dados de um barbeiro existente")
    public ResponseEntity<BarbeiroResponseDTO> atualizar(
            @Parameter(description = "ID do barbeiro")
            @PathVariable Long id,

            @Valid @RequestBody BarbeiroRequestDTO dto) {

        return ResponseEntity.ok(barbeiroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar barbeiro")
    @ApiResponse(responseCode = "204", description = "Barbeiro deletado com sucesso")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do barbeiro")
            @PathVariable Long id) {

        barbeiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}