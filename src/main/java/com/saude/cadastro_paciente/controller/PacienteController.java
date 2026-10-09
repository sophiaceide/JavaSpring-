package com.saude.cadastro_paciente.controller;


import com.saude.cadastro_paciente.business.PacienteService;
import com.saude.cadastro_paciente.infrastructure.entitys.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor

public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Void> salvarPaciente(@RequestBody Paciente paciente){
        pacienteService.salvarPaciente(paciente);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Paciente> buscarPacientePorNome(@RequestParam String nome){
        return ResponseEntity.ok(pacienteService.buscarPacientePorNome(nome));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarPacientePorNome(@RequestParam String nome){
        pacienteService.deletarPacientePorNome(nome);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarPacientePorNome(@RequestParam Paciente paciente,Integer id){
        pacienteService.atualizarPacientePorId(id, paciente);
        return ResponseEntity.ok().build();
    }


}
