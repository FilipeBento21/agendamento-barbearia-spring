package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve salvar e buscar um cliente por ID com sucesso")
    void deveSalvarEBuscarPorIdComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Hytalo Bento");
        cliente.setEmail("hytalo@email.com");
        cliente.setTelefone("81999999999");

        Cliente clientePersistido = clienteRepository.save(cliente);

        Optional<Cliente> resultado = clienteRepository.findById(clientePersistido.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Hytalo Bento");
        assertThat(resultado.get().getEmail()).isEqualTo("hytalo@email.com");
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um cliente por um ID inexistente")
    void deveRetornarVazioAoBuscarIdInexistente() {
        Long idInexistente = 99L;

        Optional<Cliente> resultado = clienteRepository.findById(idInexistente);

        assertThat(resultado).isEmpty();
    }
}