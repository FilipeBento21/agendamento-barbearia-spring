package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.ServicoService;

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
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento de serviços da barbearia")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    @Operation(summary = "Criar serviço", description = "Cadastra um novo serviço no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ServicoResponseDTO> criarServico(
            @Valid @RequestBody ServicoRequestDTO dto) {

        ServicoResponseDTO novoServico = servicoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
    }

    @GetMapping
    @Operation(summary = "Listar serviços", description = "Retorna todos os serviços cadastrados")
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
        @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public ResponseEntity<ServicoResponseDTO> buscarPorId(
            @Parameter(description = "ID do serviço")
            @PathVariable Long id) {

        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar serviço", description = "Atualiza um serviço existente")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(
            @Parameter(description = "ID do serviço")
            @PathVariable Long id,

            @Valid @RequestBody ServicoRequestDTO dto) {

        return ResponseEntity.ok(servicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço")
    @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso")
    public ResponseEntity<Void> deletarServico(
            @Parameter(description = "ID do serviço")
            @PathVariable Long id) {

        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}