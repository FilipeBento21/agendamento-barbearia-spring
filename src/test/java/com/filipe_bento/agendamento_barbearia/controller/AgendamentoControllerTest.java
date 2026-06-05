package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.AgendamentoService;
import com.filipe_bento.config.TestSecurityConfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgendamentoController.class)
@Import(TestSecurityConfig.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgendamentoService agendamentoService;

    // ── Helpers reutilizáveis ──────────────────────────────────────────────────

    private ClienteResponseDTO clienteFake() {
        return new ClienteResponseDTO(1L, "Nome", "123", "a@a.com");
    }

    private BarbeiroResponseDTO barbeiroFake() {
        return new BarbeiroResponseDTO(1L, "Nome", null);
    }

    private AgendamentoResponseDTO responseFake(LocalDateTime data) {
        return new AgendamentoResponseDTO(1L, data, clienteFake(), barbeiroFake(), Set.of());
    }

    // ── GET /api/v1/agendamentos ───────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/v1/agendamentos -> Deve retornar 200 OK")
    void deveRetornar200AoListarAgendamentos() throws Exception {
        when(agendamentoService.listarTodos()).thenReturn(List.of(responseFake(LocalDateTime.now())));

        mockMvc.perform(get("/api/v1/agendamentos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    // ── GET /api/v1/agendamentos/{id} ─────────────────────────────────────────

    @Test
    @DisplayName("GET /api/v1/agendamentos/{id} -> Deve retornar 200 quando encontrado")
    void deveRetornar200AoBuscarPorId() throws Exception {
        when(agendamentoService.buscarPorId(1L)).thenReturn(responseFake(LocalDateTime.now()));

        mockMvc.perform(get("/api/v1/agendamentos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/v1/agendamentos/{id} -> Deve retornar 404 quando não encontrado")
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(agendamentoService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Não encontrado"));

        mockMvc.perform(get("/api/v1/agendamentos/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ── POST /api/v1/agendamentos ─────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/v1/agendamentos -> Deve retornar 201 Created")
    void deveRetornar201AoCriarAgendamento() throws Exception {
        LocalDateTime dataFutura = LocalDateTime.now().plusDays(1);
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(dataFutura, 1L, 1L, Set.of(1L));

        when(agendamentoService.salvar(any(AgendamentoRequestDTO.class))).thenReturn(responseFake(dataFutura));

        mockMvc.perform(post("/api/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/v1/agendamentos -> Deve retornar 400 com body inválido")
    void deveRetornar400ComBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ── PUT /api/v1/agendamentos/{id} ─────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/v1/agendamentos/{id} -> Deve retornar 200 ao atualizar")
    void deveRetornar200AoAtualizarAgendamento() throws Exception {
        LocalDateTime dataFutura = LocalDateTime.now().plusDays(1);
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(dataFutura, 1L, 1L, Set.of(1L));

        when(agendamentoService.atualizar(eq(1L), any(AgendamentoRequestDTO.class)))
                .thenReturn(responseFake(dataFutura));

        mockMvc.perform(put("/api/v1/agendamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    // ── DELETE /api/v1/agendamentos/{id} ──────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/v1/agendamentos/{id} -> Deve retornar 204 No Content")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(agendamentoService).deletar(1L);

        mockMvc.perform(delete("/api/v1/agendamentos/1"))
                .andExpect(status().isNoContent());
    }
}