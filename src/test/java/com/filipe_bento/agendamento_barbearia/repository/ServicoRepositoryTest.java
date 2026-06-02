package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Servico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Sobe o contexto padrão do Spring Boot
@Transactional // Limpa o banco H2 automaticamente após cada teste rodar
class ServicoRepositoryTest {

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    @DisplayName("Deve salvar e buscar um serviço por ID com sucesso")
    void deveSalvarEBuscarPorIdComSucesso() {
        // ARRANGE (Preparação dos dados) - Usando o próprio repo para persistir
        Servico servico = new Servico();
        servico.setNome("Corte de Cabelo");
        servico.setPreco(35.0);

        Servico servicoPersistido = servicoRepository.save(servico);

        // ACT (Execução da ação)
        Optional<Servico> resultado = servicoRepository.findById(servicoPersistido.getId());

        // ASSERT (Verificação do resultado)
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNome()).isEqualTo("Corte de Cabelo");
        assertThat(resultado.get().getPreco()).isEqualTo(35.0);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um serviço por um ID inexistente")
    void deveRetornarVazioAoBuscarIdInexistente() {
        // ARRANGE
        Long idInexistente = 99L;

        // ACT
        Optional<Servico> resultado = servicoRepository.findById(idInexistente);

        // ASSERT
        assertThat(resultado).isEmpty();
    }
}