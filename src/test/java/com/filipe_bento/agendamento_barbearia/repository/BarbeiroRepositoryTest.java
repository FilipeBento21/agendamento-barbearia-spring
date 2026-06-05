package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BarbeiroRepositoryTest {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Test
    @DisplayName("Deve salvar e buscar um barbeiro por ID com sucesso")
    void deveSalvarEBuscarPorIdComSucesso() {
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome("Cortador Top");
        barbeiro.setEspecialidade("Cabelo e Barba");

        Barbeiro barbeiroPersistido = barbeiroRepository.save(barbeiro);

        Optional<Barbeiro> resultado = barbeiroRepository.findById(barbeiroPersistido.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Cortador Top");
        assertThat(resultado.get().getEspecialidade()).isEqualTo("Cabelo e Barba");
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um barbeiro por um ID inexistente")
    void deveRetornarVazioAoBuscarIdInexistente() {
        Long idInexistente = 99L;

        Optional<Barbeiro> resultado = barbeiroRepository.findById(idInexistente);

        assertThat(resultado).isEmpty();
    }
}