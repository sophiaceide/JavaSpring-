package com.saude.cadastro_paciente.controller;

import com.saude.cadastro_paciente.business.PacienteService;
import com.saude.cadastro_paciente.infrastructure.entitys.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> salvarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteSalvo = pacienteService.salvarPaciente(paciente);
        return ResponseEntity.ok(pacienteSalvo);
    }

    @GetMapping
    public ResponseEntity<?> buscarPacientePorNome(@RequestParam(required = false) String nome){
        if (nome == null || nome.trim().isEmpty() || nome.equalsIgnoreCase("todos") || nome.equalsIgnoreCase("all")) {
            return ResponseEntity.ok(pacienteService.listarTodos());
        }

        List<Paciente> lista = pacienteService.buscarPacientesPorNome(nome);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }

        // Se encontrou apenas um paciente, retorna o objeto diretamente (mantendo compatibilidade)
        if (lista.size() == 1) {
            return ResponseEntity.ok(lista.get(0));
        }

        // Se houver dois ou mais pacientes com o mesmo primeiro nome (ex: Maria Firmino e Maria Clara), retorna todos!
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarPacientePorNome(@RequestParam String nome){
        pacienteService.deletarPacientePorNome(nome);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarPacientePorNome(
            @RequestBody Paciente paciente,
            @RequestParam Integer id) {

        pacienteService.atualizarPacientePorId(id, paciente);
        return ResponseEntity.ok().build();
    }
}
