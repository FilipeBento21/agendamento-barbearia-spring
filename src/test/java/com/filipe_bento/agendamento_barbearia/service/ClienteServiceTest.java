package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.cliente.ClienteResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService service;

    @Test
    @DisplayName("salvar -> deve salvar com sucesso")
    void deveSalvarClienteComSucesso() {
        ClienteRequestDTO request = new ClienteRequestDTO("Cliente Teste", "123456789", "email@teste.com");
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO result = service.salvar(request);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("listarTodos -> deve retornar lista")
    void deveListarTodos() {
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente()));
        List<ClienteResponseDTO> result = service.listarTodos();
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("buscarPorId -> deve retornar quando encontrado")
    void deveBuscarPorIdComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO result = service.buscarPorId(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("buscarPorId -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
        verify(clienteRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("buscarPorNome -> deve retornar lista filtrada")
    void deveBuscarPorNomeComSucesso() {
        when(clienteRepository.findByNomeContainingIgnoreCase("Teste")).thenReturn(List.of(new Cliente()));
        List<ClienteResponseDTO> result = service.buscarPorNome("Teste");
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com todos os campos preenchidos")
    void deveAtualizarClienteComSucesso() {
        Long id = 1L;
        ClienteRequestDTO request = new ClienteRequestDTO("Novo Nome", "987654321", "novo@email.com");
        Cliente existente = new Cliente();
        existente.setId(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existente);

        ClienteResponseDTO result = service.atualizar(id, request);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com campos null (cobre branches dos ifs)")
    void deveAtualizarComCamposNulos() {
        Long id = 1L;
        ClienteRequestDTO request = new ClienteRequestDTO(null, null, null);
        Cliente existente = new Cliente();
        existente.setId(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existente);

        ClienteResponseDTO result = service.atualizar(id, request);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("atualizar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoAtualizarInexistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.atualizar(99L, new ClienteRequestDTO("N", "000", "e@e.com")));
    }

    @Test
    @DisplayName("deletar -> deve deletar com sucesso")
    void deveDeletarClienteComSucesso() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.of(new Cliente()));
        doNothing().when(clienteRepository).delete(any(Cliente.class));

        service.deletar(id);
        verify(clienteRepository, times(1)).delete(any(Cliente.class));
    }

    @Test
    @DisplayName("deletar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }
}