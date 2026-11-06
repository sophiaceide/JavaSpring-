package com.saude.cadastro_paciente.business;

import com.saude.cadastro_paciente.infrastructure.entitys.Paciente;
import com.saude.cadastro_paciente.infrastructure.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente salvarPaciente(Paciente paciente){
        return repository.saveAndFlush(paciente);
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Paciente buscarPacientePorNome(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não informado!");
        }
        String nomeFormatado = nome.trim();

        // 1. Tenta correspondência exata
        // 2. Tenta ignorando maiúsculas e minúsculas
        // 3. Tenta por primeiro nome ou parte do nome (ex: "Carlos" acha "Carlos Alberto")
        return repository.findByNome(nomeFormatado)
                .or(() -> repository.findFirstByNomeIgnoreCase(nomeFormatado))
                .or(() -> repository.findFirstByNomeContainingIgnoreCase(nomeFormatado))
                .orElseThrow(() -> new RuntimeException("Nome não encontrado!"));
    }

    public List<Paciente> buscarPacientesPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return repository.findAll();
        }
        String nomeFormatado = nome.trim();
        List<Paciente> encontrados = repository.findByNomeContainingIgnoreCase(nomeFormatado);
        if (encontrados.isEmpty()) {
            repository.findByNome(nomeFormatado).ifPresent(encontrados::add);
        }
        return encontrados;
    }

    public void deletarPacientePorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome não informado!");
        }
        String nomeFormatado = nome.trim();

        long quantidade = repository.deleteByNome(nomeFormatado);

        if (quantidade == 0) {
            quantidade = repository.deleteByNomeIgnoreCase(nomeFormatado);
        }

        if (quantidade == 0) {
            List<Paciente> encontrados = repository.findByNomeContainingIgnoreCase(nomeFormatado);
            if (!encontrados.isEmpty()) {
                repository.delete(encontrados.get(0));
                quantidade = 1;
            }
        }

        if (quantidade == 0) {
            throw new RuntimeException("Nome não encontrado!");
        }
    }

    public void atualizarPacientePorId(Integer id, Paciente paciente) {
        Paciente pacienteEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Paciente pacienteAtualizado = Paciente.builder()
                .nome(paciente.getNome() != null ? paciente.getNome() : pacienteEntity.getNome())
                .idade(paciente.getIdade() != null ? paciente.getIdade() : pacienteEntity.getIdade())
                .sintoma(paciente.getSintoma() != null ? paciente.getSintoma() : pacienteEntity.getSintoma())
                .duracaoDias(paciente.getDuracaoDias() != null ? paciente.getDuracaoDias() : pacienteEntity.getDuracaoDias())
                .doencaCronica(paciente.getDoencaCronica() != null ? paciente.getDoencaCronica() : pacienteEntity.getDoencaCronica())
                .id(pacienteEntity.getId())
                .build();

        repository.saveAndFlush(pacienteAtualizado);
    }
}
