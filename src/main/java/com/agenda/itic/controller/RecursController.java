package com.agenda.itic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.RecursRequestDTO;
import com.agenda.itic.dto.RecursResponseDTO;
import com.agenda.itic.service.RecursService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recursos")
@CrossOrigin(origins = "*")
public class RecursController {

    @Autowired
    RecursService recursService;

    @GetMapping
    public ResponseEntity<List<RecursResponseDTO>> getAllRecursos() {
        return ResponseEntity.ok(recursService.getAllRecursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursResponseDTO> getRecursById(@PathVariable Long id) {
        return ResponseEntity.ok(recursService.getRecursById(id));
    }

    @PostMapping
    public ResponseEntity<RecursResponseDTO> createRecurs(@Valid @RequestBody RecursRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recursService.createRecurs(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursResponseDTO> updateRecurs(@PathVariable Long id, @Valid @RequestBody RecursRequestDTO request) {
        return ResponseEntity.ok(recursService.updateRecurs(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurs(@PathVariable Long id) {
        recursService.deleteRecurs(id);
        return ResponseEntity.noContent().build();
    }
}