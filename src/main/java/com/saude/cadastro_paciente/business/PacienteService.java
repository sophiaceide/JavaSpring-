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
                () -> new RuntimeException("Nome não encontrado!") // roda no momento em que foi chamado
        );
    }
    public void deletarPacientePorNome(String nome){
        repository.deleteByNome(nome);
    }

    public void atualizarPacientePorId(Integer id, Paciente paciente) {

        Paciente pacienteEntity = repository.findById(id)// procura no banco paciente com certo id
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));// tratar erro

        Paciente pacienteAtualizado = Paciente.builder()// utiliza o builder e o lombok cria um metodo para construir o objeto campo por campo
                .nome(paciente.getNome() != null ? paciente.getNome() : pacienteEntity.getNome())//Se o novo nome existir, usa o novo nome. Se não existir, mantém o nome antigo.
                .idade(paciente.getIdade() != null ? paciente.getIdade() : pacienteEntity.getIdade())
                .sintoma(paciente.getSintoma() != null ? paciente.getSintoma() : pacienteEntity.getSintoma())
                .duracaoDias(paciente.getDuracaoDias() != null ? paciente.getDuracaoDias() : pacienteEntity.getDuracaoDias())
                .doencaCronica(paciente.getDoencaCronica() != null ? paciente.getDoencaCronica() : pacienteEntity.getDoencaCronica())
                .id(pacienteEntity.getId())
                .build();//finaliza o processo de construção iniciado pelo Builder e retorna o objeto Paciente com os atributos que foram definidos."

        repository.saveAndFlush(pacienteAtualizado);
    }

//O Builder é um padrão que permite construir um objeto de forma organizada, definindo seus atributos individualmente

}
