package com.filipe_bento.agendamento_barbearia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "servicos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "agendamentos")
@EqualsAndHashCode(exclude = "agendamentos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do serviço é obrigatório.")
    @Column(nullable = false, length = 100)
    private String nome;

    @DecimalMin(value = "0.0", message = "O preço deve ser positivo.")
    @Column(nullable = false)
    private Double preco;

    @JsonIgnore
    @ManyToMany(mappedBy = "servicos")
    private Set<Agendamento> agendamentos = new HashSet<>();
}