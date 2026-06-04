package com.filipe_bento.agendamento_barbearia.controller;

import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.AgendamentoService;

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
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos da barbearia")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(
            @Valid @RequestBody AgendamentoRequestDTO dto) {

        AgendamentoResponseDTO novoAgendamento = agendamentoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    @Operation(summary = "Listar agendamentos", description = "Retorna todos os agendamentos cadastrados")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(
            @Parameter(description = "ID do agendamento")
            @PathVariable Long id) {

        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agendamento", description = "Atualiza um agendamento existente")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(
            @Parameter(description = "ID do agendamento")
            @PathVariable Long id,

            @Valid @RequestBody AgendamentoRequestDTO dto) {

        return ResponseEntity.ok(agendamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar agendamento")
    @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso")
    public ResponseEntity<Void> deletarAgendamento(
            @Parameter(description = "ID do agendamento")
            @PathVariable Long id) {

        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}