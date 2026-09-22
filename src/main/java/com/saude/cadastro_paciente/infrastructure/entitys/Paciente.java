package com.saude.cadastro_paciente.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "paciente")
@Entity


public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome", length = 120)
    private String nome;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "sintoma")
    private String sintoma;

    @Column(name = "duracao_dias")
    private Integer duracaoDias;

    @Column(name = "doenca_cronica")
    private String doencaCronica;

}
