package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Usado para data e hora de agendamento:
import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByBarbeiroId(Long barbeiroId);

    List<Agendamento> findByDataHora(LocalDateTime dataHora);

}
