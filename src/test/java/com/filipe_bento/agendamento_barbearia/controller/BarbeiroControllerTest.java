package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.BarbeiroService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BarbeiroController.class)
class BarbeiroControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BarbeiroService service;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("Deve listar barbeiros")
    void deveListarBarbeiros() throws Exception {

        when(service.listarTodos()).thenReturn(
                List.of(new BarbeiroResponseDTO(1L, "João"))
        );

        mvc.perform(get("/api/v1/barbeiros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    @DisplayName("Deve criar barbeiro")
    void deveCriarBarbeiro() throws Exception {

        BarbeiroRequestDTO request = new BarbeiroRequestDTO("João");

        when(service.salvar(any())).thenReturn(
                new BarbeiroResponseDTO(1L, "João")
        );

        mvc.perform(post("/api/v1/barbeiros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("Deve deletar barbeiro")
    void deveDeletarBarbeiro() throws Exception {

        mvc.perform(delete("/api/v1/barbeiros/1"))
                .andExpect(status().isNoContent());
    }
}