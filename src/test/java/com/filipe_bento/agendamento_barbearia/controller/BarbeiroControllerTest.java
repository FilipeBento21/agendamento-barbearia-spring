package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.BarbeiroService;
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

@WebMvcTest(BarbeiroController.class)
@Import(TestSecurityConfig.class)
class BarbeiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BarbeiroService barbeiroService;

    private BarbeiroResponseDTO responseFake() {
        return new BarbeiroResponseDTO(1L, "Cortador Top", "Degradê");
    }

    @Test
    @DisplayName("POST /api/v1/barbeiros -> Deve retornar 201 Created")
    void deveRetornar201AoCriarBarbeiro() throws Exception {
        BarbeiroRequestDTO request = new BarbeiroRequestDTO("Cortador Top", "Degradê");
        when(barbeiroService.salvar(any(BarbeiroRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(post("/api/v1/barbeiros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Cortador Top"))
                .andExpect(jsonPath("$.especialidade").value("Degradê"));
    }

    @Test
    @DisplayName("POST /api/v1/barbeiros -> Deve retornar 400 com body inválido")
    void deveRetornar400ComBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/barbeiros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/barbeiros -> Deve retornar 200 OK")
    void deveRetornar200AoListarTodos() throws Exception {
        when(barbeiroService.listarTodos()).thenReturn(List.of(responseFake()));

        mockMvc.perform(get("/api/v1/barbeiros").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/v1/barbeiros/{id} -> Deve retornar 200 OK")
    void deveRetornar200AoBuscarPorId() throws Exception {
        when(barbeiroService.buscarPorId(1L)).thenReturn(responseFake());

        mockMvc.perform(get("/api/v1/barbeiros/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Cortador Top"));
    }

    @Test
    @DisplayName("GET /api/v1/barbeiros/{id} -> Deve retornar 404 se não encontrado")
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(barbeiroService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Não encontrado"));

        mockMvc.perform(get("/api/v1/barbeiros/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/barbeiros/busca -> Deve retornar 200 OK ao buscar por nome")
    void deveRetornar200AoBuscarPorNome() throws Exception {
        when(barbeiroService.buscarPorNome("Cortador")).thenReturn(List.of(responseFake()));

        mockMvc.perform(get("/api/v1/barbeiros/busca")
                        .param("nome", "Cortador")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Cortador Top"));
    }

    @Test
    @DisplayName("PUT /api/v1/barbeiros/{id} -> Deve retornar 200 ao atualizar")
    void deveRetornar200AoAtualizar() throws Exception {
        BarbeiroRequestDTO request = new BarbeiroRequestDTO("Cortador Top", "Degradê");
        when(barbeiroService.atualizar(eq(1L), any(BarbeiroRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(put("/api/v1/barbeiros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/v1/barbeiros/{id} -> Deve retornar 204 No Content")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(barbeiroService).deletar(1L);

        mockMvc.perform(delete("/api/v1/barbeiros/1"))
                .andExpect(status().isNoContent());
    }
}