package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.AgendamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private AgendamentoService service;

    // 🔹 GET LISTA
    @Test
    @DisplayName("Deve retornar 200 ao listar agendamentos")
    void deveListar() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mvc.perform(get("/api/v1/agendamentos"))
                .andExpect(status().isOk());
    }

    // 🔹 GET POR ID
    @Test
    @DisplayName("Deve retornar 200 ao buscar por ID")
    void deveBuscarPorId() throws Exception {
        AgendamentoResponseDTO response = mockResponse();

        when(service.buscarPorId(1L)).thenReturn(response);

        mvc.perform(get("/api/v1/agendamentos/1"))
                .andExpect(status().isOk());
    }

    // 🔹 GET 404
    @Test
    @DisplayName("Deve retornar 404 quando não existir")
    void deveRetornar404() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("Agendamento não encontrado"));

        mvc.perform(get("/api/v1/agendamentos/99"))
                .andExpect(status().isNotFound());
    }

    // 🔹 POST válido
    @Test
    @DisplayName("Deve retornar 201 ao criar agendamento")
    void deveCriar() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                LocalDateTime.now().plusDays(1),
                1L,
                1L,
                Set.of(1L)
        );

        when(service.salvar(any())).thenReturn(mockResponse());

        mvc.perform(post("/api/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    // 🔹 POST inválido (data no passado)
    @Test
    @DisplayName("Deve retornar 400 para data inválida")
    void deveRetornar400() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                LocalDateTime.now().minusDays(1), // inválido
                null,
                null,
                Set.of()
        );

        mvc.perform(post("/api/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // 🔹 PUT
    @Test
    @DisplayName("Deve retornar 200 ao atualizar agendamento")
    void deveAtualizar() throws Exception {
        AgendamentoRequestDTO request = new AgendamentoRequestDTO(
                LocalDateTime.now().plusDays(2),
                1L,
                1L,
                Set.of(1L)
        );

        when(service.atualizar(any(Long.class), any())).thenReturn(mockResponse());

        mvc.perform(put("/api/v1/agendamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // 🔹 DELETE
    @Test
    @DisplayName("Deve retornar 204 ao deletar agendamento")
    void deveDeletar() throws Exception {
        mvc.perform(delete("/api/v1/agendamentos/1"))
                .andExpect(status().isNoContent());
    }

    // 🔹 Mock helper
    private AgendamentoResponseDTO mockResponse() {
        return new AgendamentoResponseDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                null,
                null,
                Set.of()
        );
    }
}