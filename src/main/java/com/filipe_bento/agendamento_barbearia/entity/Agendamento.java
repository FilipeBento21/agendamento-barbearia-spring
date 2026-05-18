package com.filipe_bento.agendamento_barbearia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agendamentos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "servicos")
@EqualsAndHashCode(exclude = "servicos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data e hora são obrigatórias.")
    private LocalDateTime dataHora;

    // MUITOS → UM cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // MUITOS → UM barbeiro
    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    // MUITOS ↔ MUITOS serviços (lado DONO)
    @ManyToMany
    @JoinTable(
        name = "agendamento_servico",
        joinColumns = @JoinColumn(name = "agendamento_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicos = new HashSet<>();
}