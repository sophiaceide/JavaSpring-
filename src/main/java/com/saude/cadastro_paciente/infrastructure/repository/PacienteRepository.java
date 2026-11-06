package com.saude.cadastro_paciente.infrastructure.repository;

import com.saude.cadastro_paciente.infrastructure.entitys.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByNome(String nome);

    Optional<Paciente> findFirstByNomeIgnoreCase(String nome);

    Optional<Paciente> findFirstByNomeContainingIgnoreCase(String nome);

    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    @Transactional
    long deleteByNome(String nome);

    @Transactional
    long deleteByNomeIgnoreCase(String nome);
}
