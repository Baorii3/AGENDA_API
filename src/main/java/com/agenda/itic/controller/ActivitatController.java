package com.agenda.itic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.ActivitatRequestDTO;
import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.service.ActivitatService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/activitats")
@CrossOrigin(origins = "*")
public class ActivitatController {

    @Autowired
    ActivitatService activitatService;
    @GetMapping("/model")
    public ResponseEntity<List<Activitat>> getActivitatModel() {
        return ResponseEntity.ok(activitatService.getActivitatModel());
    }
    @GetMapping
    public ResponseEntity<List<ActivitatResponseDTO>> getAllActivitats() {
        return ResponseEntity.status(HttpStatus.OK).body(activitatService.getAllActivitats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivitatResponseDTO> getActivitatById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(activitatService.getActivitatById(id));
    }

    @GetMapping("/usuari/{idUsuari}")
    public ResponseEntity<List<ActivitatResponseDTO>> getActivitatsByUsuari(@PathVariable Long idUsuari) {
        return ResponseEntity.status(HttpStatus.OK).body(activitatService.getActivitatsByUsuari(idUsuari));
    }

    @PostMapping
    public ResponseEntity<ActivitatResponseDTO> createActivitat(@Valid @RequestBody ActivitatRequestDTO activitatRequestDTO) {
        ActivitatResponseDTO response = activitatService.createActivitat(activitatRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivitat(@PathVariable Long id) {
        activitatService.deleteActivitat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllActivitats() {
        activitatService.getActivitatModel().forEach(a -> activitatService.deleteActivitat(a.getId_activitat()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
