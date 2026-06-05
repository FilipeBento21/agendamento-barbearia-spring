package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.ClienteService;
import com.filipe_bento.config.TestSecurityConfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
@Import(TestSecurityConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    private ClienteResponseDTO responseFake() {
        return new ClienteResponseDTO(1L, "Hytalo Bento", "81999999999", "hytalo@email.com");
    }

    @Test
    @DisplayName("POST /api/v1/clientes -> Deve retornar 201 Created")
    void deveRetornar201AoCriarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Hytalo Bento", "81999999999", "hytalo@email.com");
        when(clienteService.salvar(any(ClienteRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Hytalo Bento"));
    }

    @Test
    @DisplayName("POST /api/v1/clientes -> Deve retornar 400 com body inválido")
    void deveRetornar400ComBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/clientes -> Deve retornar 200 OK")
    void deveRetornar200AoListarTodos() throws Exception {
        when(clienteService.listarTodos()).thenReturn(List.of(responseFake()));

        mockMvc.perform(get("/api/v1/clientes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} -> Deve retornar 200 OK")
    void deveRetornar200AoBuscarPorId() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(responseFake());

        mockMvc.perform(get("/api/v1/clientes/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Hytalo Bento"));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} -> Deve retornar 404 quando não encontrado")
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(clienteService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        mockMvc.perform(get("/api/v1/clientes/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/clientes/busca -> Deve retornar 200 OK ao buscar por nome")
    void deveRetornar200AoBuscarPorNome() throws Exception {
        when(clienteService.buscarPorNome("Hytalo")).thenReturn(List.of(responseFake()));

        mockMvc.perform(get("/api/v1/clientes/busca")
                        .param("nome", "Hytalo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Hytalo Bento"));
    }

    @Test
    @DisplayName("PUT /api/v1/clientes/{id} -> Deve retornar 200 ao atualizar")
    void deveRetornar200AoAtualizar() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Hytalo Bento", "81999999999", "hytalo@email.com");
        when(clienteService.atualizar(eq(1L), any(ClienteRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/v1/clientes/{id} -> Deve retornar 204 No Content")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(clienteService).deletar(1L);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }
}