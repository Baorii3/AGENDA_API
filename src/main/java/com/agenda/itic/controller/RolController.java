package com.agenda.itic.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.config.SecurityExpressions;
import com.agenda.itic.dto.RolRequestDTO;
import com.agenda.itic.dto.RolResponseDTO;
import com.agenda.itic.service.RolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
@PreAuthorize(SecurityExpressions.IS_ADMIN)
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> getRolById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> createRol(@Valid @RequestBody RolRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.createRol(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> updateRol(@PathVariable Long id, @Valid @RequestBody RolRequestDTO request) {
        return ResponseEntity.ok(rolService.updateRol(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}