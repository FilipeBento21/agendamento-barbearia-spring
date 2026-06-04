package com.filipe_bento.agendamento_barbearia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.service.ServicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicoController.class)
class ServicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServicoService service;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("Deve listar serviços")
    void deveListarServicos() throws Exception {

        when(service.listarTodos()).thenReturn(
                List.of(new ServicoResponseDTO(1L, "Corte", BigDecimal.valueOf(30)))
        );

        mvc.perform(get("/api/v1/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte"));
    }

    @Test
    @DisplayName("Deve criar serviço")
    void deveCriarServico() throws Exception {

        ServicoRequestDTO request =
                new ServicoRequestDTO("Corte", BigDecimal.valueOf(30));

        when(service.salvar(any())).thenReturn(
                new ServicoResponseDTO(1L, "Corte", BigDecimal.valueOf(30))
        );

        mvc.perform(post("/api/v1/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Corte"));
    }

    @Test
    @DisplayName("Deve atualizar serviço")
    void deveAtualizarServico() throws Exception {

        ServicoRequestDTO request =
                new ServicoRequestDTO("Corte Premium", BigDecimal.valueOf(50));

        when(service.atualizar(any(), any())).thenReturn(
                new ServicoResponseDTO(1L, "Corte Premium", BigDecimal.valueOf(50))
        );

        mvc.perform(put("/api/v1/servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte Premium"));
    }

    @Test
    @DisplayName("Deve deletar serviço")
    void deveDeletarServico() throws Exception {

        mvc.perform(delete("/api/v1/servicos/1"))
                .andExpect(status().isNoContent());
    }
}