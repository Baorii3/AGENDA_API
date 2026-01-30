package com.agenda.itic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.SalaRequest;
import com.agenda.itic.model.Sala;
import com.agenda.itic.service.SalaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    SalaService salaService;

    @GetMapping("/salas")
    public ResponseEntity<List<Sala>> getAllSalas() {
        List<Sala> salas = salaService.getAllSalas();
        return ResponseEntity.status(HttpStatus.OK).body(salas);
    }

    @PostMapping("/salas")
    public ResponseEntity<Sala> postSala(@RequestBody SalaRequest salaRequest) {
        Sala sala = salaService.postSala(salaRequest);
        if (sala != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(sala);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/salas/{id}")
    public ResponseEntity<Sala> putSala(@PathVariable Long id, @RequestBody SalaRequest salaRequest) {
        Sala salaActualitzada = salaService.putSala(id, salaRequest);
        if (salaActualitzada != null) {
            return ResponseEntity.status(HttpStatus.OK).body(salaActualitzada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/salas/{id}")
    public ResponseEntity<String> deleteSala(@PathVariable Long id) {
        boolean eliminat = salaService.deleteSala(id);
        if (eliminat) {
            return ResponseEntity.status(HttpStatus.OK).body("Sala elimina correctament");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala amb id " + id + " no trobada");
        }
    }
}