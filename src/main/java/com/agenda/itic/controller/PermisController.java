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

import com.agenda.itic.dto.PermisRequestDTO;
import com.agenda.itic.dto.PermisResponseDTO;
import com.agenda.itic.service.PermisService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permisos")
@CrossOrigin(origins = "*")
public class PermisController {

    @Autowired
    PermisService permisService;

    @GetMapping
    public ResponseEntity<List<PermisResponseDTO>> getAllPermisos() {
        return ResponseEntity.ok(permisService.getAllPermisos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisResponseDTO> getPermisById(@PathVariable Long id) {
        return ResponseEntity.ok(permisService.getPermisById(id));
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<PermisResponseDTO>> getPermisosByRol(@PathVariable String rol) {
        return ResponseEntity.ok(permisService.getPermisosByRol(rol));
    }

    @GetMapping("/recurs/{idRecurs}")
    public ResponseEntity<List<PermisResponseDTO>> getPermisosByRecurs(@PathVariable Long idRecurs) {
        return ResponseEntity.ok(permisService.getPermisosByRecurs(idRecurs));
    }

    @PostMapping
    public ResponseEntity<PermisResponseDTO> createPermis(@Valid @RequestBody PermisRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permisService.createPermis(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisResponseDTO> updatePermis(@PathVariable Long id, @Valid @RequestBody PermisRequestDTO request) {
        return ResponseEntity.ok(permisService.updatePermis(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermis(@PathVariable Long id) {
        permisService.deletePermis(id);
        return ResponseEntity.noContent().build();
    }
}