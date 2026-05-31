package com.filipe_bento.agendamento_barbearia.repository;

import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);
}