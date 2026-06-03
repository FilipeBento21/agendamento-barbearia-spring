package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Servico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ServicoRepositoryTest {

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    @DisplayName("Deve salvar e buscar um serviço por ID com sucesso")
    void deveSalvarEBuscarPorIdComSucesso() {
        
        Servico servico = new Servico();
        servico.setNome("Corte de Cabelo");
        servico.setPreco(35.0);

        Servico servicoPersistido = servicoRepository.save(servico);

        
        Optional<Servico> resultado = servicoRepository.findById(servicoPersistido.getId());

        
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Corte de Cabelo");
        assertThat(resultado.get().getPreco()).isEqualTo(35.0);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um serviço por um ID inexistente")
    void deveRetornarVazioAoBuscarIdInexistente() {
        
        Long idInexistente = 99L;

        
        Optional<Servico> resultado = servicoRepository.findById(idInexistente);

        
        assertThat(resultado).isEmpty();
    }
}