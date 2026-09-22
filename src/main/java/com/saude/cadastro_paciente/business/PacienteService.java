package com.saude.cadastro_paciente.business;

import com.saude.cadastro_paciente.infrastructure.entitys.Paciente;
import com.saude.cadastro_paciente.infrastructure.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public void salvarPaciente(Paciente paciente){
        repository.saveAndFlush(paciente);
    }

    public Paciente buscarPacientePorNome(String nome){

        return repository.findByNome(nome).orElseThrow(
                () -> new RuntimeException("Nome não encontrado!")
        );
    }



}
