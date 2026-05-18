package com.filipe_bento.agendamento_barbearia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "barbeiros")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "agendamentos")
@EqualsAndHashCode(exclude = "agendamentos")
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do barbeiro é obrigatório.")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String especialidade;

    @JsonIgnore
    @OneToMany(mappedBy = "barbeiro")
    private List<Agendamento> agendamentos = new ArrayList<>();
}