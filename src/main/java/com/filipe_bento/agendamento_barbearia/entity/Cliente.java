package com.filipe_bento.agendamento_barbearia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter 
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "agendamentos")
@EqualsAndHashCode(exclude = "agendamentos")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O celular do cliente é obrigatório.")
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotBlank(message = "O email do cliente é obrigatório.")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    // RELACIONAMENTO (AGORA NO LUGAR CERTO)
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Agendamento> agendamentos = new ArrayList<>();
}