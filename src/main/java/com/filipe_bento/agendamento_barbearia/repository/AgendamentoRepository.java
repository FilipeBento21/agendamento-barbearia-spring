package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByBarbeiroId(Long barbeiroId);

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    boolean existsByBarbeiroIdAndDataHora(Long barbeiroId, LocalDateTime dataHora);

    @Query("SELECT a FROM Agendamento a WHERE a.barbeiro.id = :barbeiroId AND a.dataHora BETWEEN :inicio AND :fim")
    List<Agendamento> buscarPorBarbeiroEPeriodo(
        @Param("barbeiroId") Long barbeiroId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
}