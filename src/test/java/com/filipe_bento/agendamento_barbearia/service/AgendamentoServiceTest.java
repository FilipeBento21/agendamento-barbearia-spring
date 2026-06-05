package com.filipe_bento.agendamento_barbearia.service;

import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoRequestDTO;
import com.filipe_bento.agendamento_barbearia.dto.agendamento.AgendamentoResponseDTO;
import com.filipe_bento.agendamento_barbearia.entity.*;
import com.filipe_bento.agendamento_barbearia.exception.ResourceNotFoundException;
import com.filipe_bento.agendamento_barbearia.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock private AgendamentoRepository agendamentoRepository;
    @Mock private ClienteRepository clienteRepository;
    @Mock private BarbeiroRepository barbeiroRepository;
    @Mock private ServicoRepository servicoRepository;

    @InjectMocks
    private AgendamentoService service;

    private Agendamento agendamento;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private Servico servico;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        barbeiro = new Barbeiro();
        barbeiro.setId(1L);
        servico = new Servico();
        servico.setId(1L);
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setDataHora(LocalDateTime.now().plusDays(1));
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServicos(Set.of(servico));
    }

    @Test
    @DisplayName("listarTodos -> deve retornar lista")
    void deveListarTodos() {
        when(agendamentoRepository.findAll()).thenReturn(List.of(agendamento));
        List<AgendamentoResponseDTO> lista = service.listarTodos();
        assertThat(lista).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorId -> deve retornar quando encontrado")
    void deveBuscarPorId() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        AgendamentoResponseDTO result = service.buscarPorId(1L);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("buscarPorId -> deve lançar exceção quando não encontrado")
    void deveLancarErroEmBuscarPorId() {
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    @DisplayName("salvar -> deve salvar com sucesso")
    void deveSalvarComSucesso() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 1L, Set.of(1L));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(agendamentoRepository.save(any())).thenReturn(agendamento);

        service.salvar(req);
        verify(agendamentoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("salvar -> deve lançar exceção quando cliente não encontrado")
    void deveLancarExcecaoAoSalvarClienteInexistente() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 99L, 1L, Set.of(1L));
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.salvar(req));
    }

    @Test
    @DisplayName("salvar -> deve lançar exceção quando barbeiro não encontrado")
    void deveLancarExcecaoAoSalvarBarbeiroInexistente() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 99L, Set.of(1L));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(barbeiroRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.salvar(req));
    }

    @Test
    @DisplayName("salvar -> deve lançar exceção quando serviço não encontrado")
    void deveLancarExcecaoAoSalvarServicoInexistente() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 1L, Set.of(99L));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.salvar(req));
    }

    @Test
    @DisplayName("deletar -> deve deletar com sucesso")
    void deveDeletarComSucesso() {
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        service.deletar(1L);
        verify(agendamentoRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("deletar -> deve lançar exceção quando não encontrado")
    void deveLancarExcecaoAoDeletar() {
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));
    }

    @Test
    @DisplayName("atualizar -> deve atualizar todos os campos")
    void deveAtualizarTudo() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 1L, Set.of(1L));
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(barbeiroRepository.findById(1L)).thenReturn(Optional.of(barbeiro));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(agendamentoRepository.save(any())).thenReturn(agendamento);

        service.atualizar(1L, req);
        verify(agendamentoRepository).save(any());
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com campos null (cobre branches dos ifs)")
    void deveAtualizarComCamposNulos() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(null, null, null, null);
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(agendamentoRepository.save(any())).thenReturn(agendamento);

        service.atualizar(1L, req);
        verify(agendamentoRepository).save(any());
    }

    @Test
    @DisplayName("atualizar -> deve atualizar com servicosIds vazio (cobre branch isEmpty)")
    void deveAtualizarComServicosVazios() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), null, null, Set.of());
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(agendamentoRepository.save(any())).thenReturn(agendamento);

        service.atualizar(1L, req);
        verify(agendamentoRepository).save(any());
    }

    @Test
    @DisplayName("atualizar -> deve lançar exceção quando agendamento não encontrado")
    void deveLancarExcecaoAoAtualizarInexistente() {
        AgendamentoRequestDTO req = new AgendamentoRequestDTO(LocalDateTime.now(), 1L, 1L, Set.of(1L));
        when(agendamentoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.atualizar(99L, req));
    }
}