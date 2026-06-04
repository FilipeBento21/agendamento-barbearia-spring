package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BarbeiroRepositoryTest {

    @Autowired
    private BarbeiroRepository repository;

    @Test
    @DisplayName("Deve buscar barbeiro por nome ignorando maiúsculas/minúsculas")
    void deveBuscarPorNome() {
        // ARRANGE
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome("Carlos Silva");

        repository.save(barbeiro);

        // ACT
        List<Barbeiro> resultado = repository.findByNomeContainingIgnoreCase("carlos");

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNome()).isEqualTo("Carlos Silva");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando barbeiro não existir")
    void deveRetornarVazio() {
        // ACT
        List<Barbeiro> resultado = repository.findByNomeContainingIgnoreCase("inexistente");

        // ASSERT
        assertThat(resultado).isEmpty();
    }
}