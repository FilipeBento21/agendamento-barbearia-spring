package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private ClienteService service;

    // 🔹 GET /clientes
    @Test
    @DisplayName("Deve retornar 200 ao listar clientes")
    void deveListarClientes() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk());
    }

    // 🔹 GET por ID
    @Test
    @DisplayName("Deve retornar 200 ao buscar por ID")
    void deveBuscarPorId() throws Exception {
        ClienteResponseDTO response = new ClienteResponseDTO(1L, "Filipe", "81999999999", "filipe@email.com");

        when(service.buscarPorId(1L)).thenReturn(response);

        mvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Filipe"));
    }

    // 🔹 GET ID inexistente
    @Test
    @DisplayName("Deve retornar 404 quando cliente não existir")
    void deveRetornar404() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("Cliente não encontrado"));

        mvc.perform(get("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }

    // 🔹 POST válido
    @Test
    @DisplayName("Deve retornar 201 ao criar cliente")
    void deveCriarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Filipe", "81999999999", "filipe@email.com");
        ClienteResponseDTO response = new ClienteResponseDTO(1L, "Filipe", "81999999999", "filipe@email.com");

        when(service.salvar(any())).thenReturn(response);

        mvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    // 🔹 POST inválido
    @Test
    @DisplayName("Deve retornar 400 ao enviar dados inválidos")
    void deveRetornar400() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("", "", "email-invalido");

        mvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // 🔹 PUT
    @Test
    @DisplayName("Deve retornar 200 ao atualizar cliente")
    void deveAtualizarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("Novo Nome", "81999999999", "novo@email.com");
        ClienteResponseDTO response = new ClienteResponseDTO(1L, "Novo Nome", "81999999999", "novo@email.com");

        when(service.atualizar(any(Long.class), any())).thenReturn(response);

        mvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }

    // 🔹 DELETE
    @Test
    @DisplayName("Deve retornar 204 ao deletar cliente")
    void deveDeletarCliente() throws Exception {
        mvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }
}