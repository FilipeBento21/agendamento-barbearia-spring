package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.servico.ServicoResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Servico;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.ServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService service;

    @Test
    @DisplayName("salvar -> deve salvar com sucesso")
    void deveSalvarServicoComSucesso() {
        BigDecimal preco = new BigDecimal("50.0");
        ServicoRequestDTO request = new ServicoRequestDTO("Corte", preco);
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");
        servico.setPreco(preco.doubleValue());

        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        ServicoResponseDTO result = service.salvar(request);
        assertThat(result.nome()).isEqualTo("Corte");
        verify(servicoRepository, times(1)).save(any(Servico.class));
    }

    @Test
    @DisplayName("salvar -> deve salvar com preco null (cobre branch do if)")
    void deveSalvarComPrecoNulo() {
        ServicoRequestDTO request = new ServicoRequestDTO("Corte", null);
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Corte");

        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        ServicoResponseDTO result = service.salvar(request);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("listarTodos -> deve retornar lista")
    void deveListarTodos() {
        when(servicoRepository.findAll()).thenReturn(List.of(new Servico()));
        List<ServicoResponseDTO> result = service.listarTodos();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorId -> deve retornar quando encontrado")
    void deveBuscarPorIdComSucesso() {
        Servico servico = new Servico();
        servico.setId(1L);
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));

        ServicoResponseDTO result = service.buscarPorId(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("buscarPorId -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(servicoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com todos os campos")
    void deveAtualizarComSucesso() {
        Long id = 1L;
        ServicoRequestDTO request = new ServicoRequestDTO("Corte Premium", new BigDecimal("80.0"));
        Servico existente = new Servico();
        existente.setId(id);

        when(servicoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(servicoRepository.save(any(Servico.class))).thenReturn(existente);

        ServicoResponseDTO result = service.atualizar(id, request);
        assertThat(result).isNotNull();
        verify(servicoRepository).save(any(Servico.class));
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com campos null (cobre branches dos ifs)")
    void deveAtualizarComCamposNulos() {
        Long id = 1L;
        ServicoRequestDTO request = new ServicoRequestDTO(null, null);
        Servico existente = new Servico();
        existente.setId(id);

        when(servicoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(servicoRepository.save(any(Servico.class))).thenReturn(existente);

        ServicoResponseDTO result = service.atualizar(id, request);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("atualizar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoAtualizarInexistente() {
        when(servicoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(99L, new ServicoRequestDTO("Nome", BigDecimal.TEN)));
    }

    @Test
    @DisplayName("deletar -> deve deletar com sucesso")
    void deveDeletarComSucesso() {
        Long id = 1L;
        when(servicoRepository.findById(id)).thenReturn(Optional.of(new Servico()));
        doNothing().when(servicoRepository).delete(any(Servico.class));

        service.deletar(id);
        verify(servicoRepository, times(1)).delete(any(Servico.class));
    }

    @Test
    @DisplayName("deletar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(servicoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}