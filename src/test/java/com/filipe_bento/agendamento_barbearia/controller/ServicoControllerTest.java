package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.service.ServicoService;
import com.filipe_bento.config.TestSecurityConfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServicoController.class)
@Import(TestSecurityConfig.class)
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ServicoService servicoService;

    private ServicoResponseDTO responseFake() {
        return new ServicoResponseDTO(1L, "Cabelo e Barba", BigDecimal.valueOf(50.0));
    }

    @Test
    @DisplayName("POST /api/v1/servicos -> Deve retornar 201 Created")
    void deveRetornar201AoCriarServico() throws Exception {
        ServicoRequestDTO request = new ServicoRequestDTO("Cabelo e Barba", BigDecimal.valueOf(50.0));
        when(servicoService.salvar(any(ServicoRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(post("/api/v1/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Cabelo e Barba"))
                .andExpect(jsonPath("$.preco").value(50.0));
    }

    @Test
    @DisplayName("POST /api/v1/servicos -> Deve retornar 400 com body inválido")
    void deveRetornar400ComBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/servicos -> Deve retornar 200 OK")
    void deveRetornar200AoListarTodos() throws Exception {
        when(servicoService.listarTodos()).thenReturn(List.of(responseFake()));

        mockMvc.perform(get("/api/v1/servicos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/v1/servicos/{id} -> Deve retornar 200 OK")
    void deveRetornar200AoBuscarPorId() throws Exception {
        when(servicoService.buscarPorId(1L)).thenReturn(responseFake());

        mockMvc.perform(get("/api/v1/servicos/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Cabelo e Barba"));
    }

    @Test
    @DisplayName("GET /api/v1/servicos/{id} -> Deve retornar 404 quando não encontrado")
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(servicoService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Não encontrado"));

        mockMvc.perform(get("/api/v1/servicos/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/servicos/{id} -> Deve retornar 200 ao atualizar")
    void deveRetornar200AoAtualizar() throws Exception {
        ServicoRequestDTO request = new ServicoRequestDTO("Cabelo e Barba", BigDecimal.valueOf(50.0));
        when(servicoService.atualizar(eq(1L), any(ServicoRequestDTO.class))).thenReturn(responseFake());

        mockMvc.perform(put("/api/v1/servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/v1/servicos/{id} -> Deve retornar 204 No Content")
    void deveRetornar204AoDeletar() throws Exception {
        doNothing().when(servicoService).deletar(1L);

        mockMvc.perform(delete("/api/v1/servicos/1"))
                .andExpect(status().isNoContent());
    }
}