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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private ServicoService service;

    @Test
    @DisplayName("Deve salvar um serviço com sucesso e retornar o ResponseDTO correspondente")
    void deveSalvarServicoComSucesso() {
        
        ServicoRequestDTO requestDTO = new ServicoRequestDTO("Cabelo e Barba", BigDecimal.valueOf(50.0));
        
        Servico servicoSalvo = new Servico();
        servicoSalvo.setId(1L);
        servicoSalvo.setNome("Cabelo e Barba");
        servicoSalvo.setPreco(50.0);

    
        when(repository.save(any(Servico.class))).thenReturn(servicoSalvo);

        
        ServicoResponseDTO resultado = service.salvar(requestDTO);

    
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.nome()).isEqualTo("Cabelo e Barba");
        assertThat(resultado.preco()).isEqualByComparingTo(BigDecimal.valueOf(50.0));
        
        verify(repository, times(1)).save(any(Servico.class)); // Garante que o método save do banco foi chamado 1 vez
    }

    @Test
    @DisplayName("Deve buscar serviço por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        
        Long id = 1L;
        Servico servico = new Servico();
        servico.setId(id);
        servico.setNome("Corte");
        servico.setPreco(30.0);

        when(repository.findById(id)).thenReturn(Optional.of(servico));

        
        ServicoResponseDTO resultado = service.buscarPorId(id);

        
        assertThat(resultado).isNotNull();
        assertThat(resultado.nome()).isEqualTo("Corte");
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        
        Long idInexistente = 99L;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> service.buscarPorId(idInexistente))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Serviço não encontrado com o ID: " + idInexistente);
                
        verify(repository, times(1)).findById(idInexistente);
    }
}