package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import com.filipe_bento.agendamento_barbearia.entity.Cliente;
import com.filipe_bento.agendamento_barbearia.entity.Servico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Garante a carga completa do contexto do Spring Boot
@Transactional // Limpa o banco H2 automaticamente ao fim de cada teste
class AgendamentoRepositoryTest {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Test
    @DisplayName("Deve salvar e buscar um agendamento por ID com sucesso")
    void deveSalvarEBuscarAgendamentoComSucesso() {
        // ARRANGE - Criando e salvando as entidades diretamente via seus repositories
        Cliente cliente = new Cliente();
        cliente.setNome("Hytalo");
        cliente.setTelefone("81999999999");
        cliente.setEmail("hytalo@email.com");
        cliente = clienteRepository.save(cliente);

        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setNome("Filipe Bento");
        barbeiro.setEspecialidade("Barba");
        barbeiro = barbeiroRepository.save(barbeiro);

        Servico servico = new Servico();
        servico.setNome("Corte Simples");
        servico.setPreco(25.0);
        servico = servicoRepository.save(servico);

        // Criando e associando o agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServicos(Set.of(servico));
        agendamento.setDataHora(LocalDateTime.now());

        // ACT
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        Optional<Agendamento> agendamentoBuscado = agendamentoRepository.findById(agendamentoSalvo.getId());

        // ASSERT [cite: 377]
        assertThat(agendamentoBuscado).isPresent();
        assertThat(agendamentoBuscado.get().getCliente().getNome()).isEqualTo("Hytalo");
        assertThat(agendamentoBuscado.get().getBarbeiro().getNome()).isEqualTo("Filipe Bento");
        assertThat(agendamentoBuscado.get().getServicos()).hasSize(1);
    }
}