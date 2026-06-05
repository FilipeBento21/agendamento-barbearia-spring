package com.filipe_bento.agendamento_barbearia.exception;

import com.filipe_bento.agendamento_barbearia.controller.BarbeiroController;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BarbeiroController.class)
@Import(TestSecurityConfig.class)
class ValidationErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BarbeiroService barbeiroService;

    @Test
    @DisplayName("Deve retornar 404 e estrutura de erro quando ResourceNotFoundException for lançada")
    void deveRetornarErroPadraoQuandoRecursoNaoEncontrado() throws Exception {
        when(barbeiroService.buscarPorId(anyLong()))
            .thenThrow(new ResourceNotFoundException("Barbeiro não encontrado com o ID: 99"));

        mockMvc.perform(get("/api/v1/barbeiros/99")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Barbeiro não encontrado com o ID: 99"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando payload JSON for inválido (SecurityExceptionHandler)")
    void deveRetornar400QuandoPayloadInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/barbeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ json malformado }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Requisição Inválida"));
    }
}