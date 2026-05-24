package com.filipe_bento.agendamento_barbearia.repository;


// imports:
import com.filipe_bento.agendamento_barbearia.entity.Barbeiro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BarbeiroRepository extends CrudRepository<Barbeiro, Long> {
    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);
}
