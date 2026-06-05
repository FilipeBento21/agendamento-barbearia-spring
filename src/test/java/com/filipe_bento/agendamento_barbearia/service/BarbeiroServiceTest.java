package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.barbeiro.BarbeiroResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.BarbeiroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarbeiroServiceTest {

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @InjectMocks
    private BarbeiroService service;

    @Test
    @DisplayName("salvar -> deve salvar com sucesso")
    void deveSalvarBarbeiroComSucesso() {
        BarbeiroRequestDTO request = new BarbeiroRequestDTO("Barbeiro Teste", "Degradê");
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setNome("Barbeiro Teste");
        barbeiro.setEspecialidade("Degradê");

        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);

        BarbeiroResponseDTO result = service.salvar(request);
        assertThat(result.nome()).isEqualTo("Barbeiro Teste");
        assertThat(result.especialidade()).isEqualTo("Degradê");
        verify(barbeiroRepository, times(1)).save(any(Barbeiro.class));
    }

    @Test
    @DisplayName("salvar -> deve salvar sem especialidade")
    void deveSalvarBarbeiroSemEspecialidade() {
        BarbeiroRequestDTO request = new BarbeiroRequestDTO("Barbeiro Teste", null);
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        barbeiro.setNome("Barbeiro Teste");

        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);

        BarbeiroResponseDTO result = service.salvar(request);
        assertThat(result.nome()).isEqualTo("Barbeiro Teste");
        verify(barbeiroRepository, times(1)).save(any(Barbeiro.class));
    }

    @Test
    @DisplayName("buscarPorId -> deve retornar quando encontrado")
    void deveBuscarPorIdComSucesso() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiro));

        BarbeiroResponseDTO result = service.buscarPorId(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("buscarPorId -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoBuscarInexistente() {
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    @DisplayName("listarTodos -> deve retornar lista")
    void deveListarTodos() {
        when(barbeiroRepository.findAll()).thenReturn(List.of(new Barbeiro()));
        List<BarbeiroResponseDTO> result = service.listarTodos();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorNome -> deve retornar lista filtrada")
    void deveBuscarPorNome() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome("Cortador Top");
        when(barbeiroRepository.findByNomeContainingIgnoreCase("Cortador")).thenReturn(List.of(barbeiro));

        List<BarbeiroResponseDTO> result = service.buscarPorNome("Cortador");
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com sucesso")
    void deveAtualizarComSucesso() {
        Long id = 1L;
        BarbeiroRequestDTO request = new BarbeiroRequestDTO("Novo Nome", "Tesoura");
        Barbeiro existente = new Barbeiro();
        existente.setId(id);

        when(barbeiroRepository.findById(id)).thenReturn(Optional.of(existente));
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(existente);

        BarbeiroResponseDTO result = service.atualizar(id, request);
        assertThat(result).isNotNull();
        verify(barbeiroRepository).save(any(Barbeiro.class));
    }

    @Test
    @DisplayName("atualizar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoAtualizarInexistente() {
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(99L, new BarbeiroRequestDTO("Nome", null)));
    }

    @Test
    @DisplayName("deletar -> deve deletar com sucesso")
    void deveDeletarComSucesso() {
        Long id = 1L;
        when(barbeiroRepository.findById(id)).thenReturn(Optional.of(new Barbeiro()));
        doNothing().when(barbeiroRepository).delete(any(Barbeiro.class));

        service.deletar(id);
        verify(barbeiroRepository, times(1)).delete(any(Barbeiro.class));
    }

    @Test
    @DisplayName("deletar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}