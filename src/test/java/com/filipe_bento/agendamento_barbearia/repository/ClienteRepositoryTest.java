package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    @DisplayName("Deve buscar cliente por nome ignorando maiúsculas/minúsculas")
    void deveBuscarPorNome() {
        // ARRANGE
        Cliente cliente = new Cliente();
        cliente.setNome("Filipe Bento");
        cliente.setTelefone("81999999999");
        cliente.setEmail("filipe@email.com");

        repository.save(cliente);

        // ACT
        List<Cliente> resultado = repository.findByNomeContainingIgnoreCase("filipe");

        // ASSERT
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNome()).isEqualTo("Filipe Bento");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nome não existir")
    void deveRetornarVazio() {
        // ACT
        List<Cliente> resultado = repository.findByNomeContainingIgnoreCase("inexistente");

        // ASSERT
        assertThat(resultado).isEmpty();
    }
}