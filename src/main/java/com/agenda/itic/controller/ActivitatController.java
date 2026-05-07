package com.agenda.itic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.config.SecurityExpressions;
import com.agenda.itic.dto.ActivitatRequestDTO;
import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.service.ActivitatService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/activitats")
@CrossOrigin(origins = "*")
public class ActivitatController {

    @Autowired
    ActivitatService activitatService;

    @GetMapping("/model")
    @PreAuthorize(SecurityExpressions.ACTIVITAT_READ)
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
    @PreAuthorize(SecurityExpressions.ACTIVITAT_CREATE)
    public ResponseEntity<ActivitatResponseDTO> createActivitat(
            @Valid @RequestBody ActivitatRequestDTO peticioActivitatDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activitatService.createActivitat(peticioActivitatDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize(SecurityExpressions.ACTIVITAT_UPDATE)
    public ResponseEntity<ActivitatResponseDTO> updateActivitat(@PathVariable Long id,
            @Valid @RequestBody ActivitatRequestDTO peticioActivitatDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(activitatService.updateActivitat(id, peticioActivitatDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(SecurityExpressions.ACTIVITAT_DELETE)
    public ResponseEntity<Void> deleteActivitat(@PathVariable Long id) {
        activitatService.deleteActivitat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    @PreAuthorize(SecurityExpressions.ACTIVITAT_DELETE)
    public ResponseEntity<Void> deleteAllActivitats() {
        activitatService.getActivitatModel().forEach(a -> activitatService.deleteActivitat(a.getId()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
