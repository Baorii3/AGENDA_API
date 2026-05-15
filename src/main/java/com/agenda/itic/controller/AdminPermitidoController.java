package com.agenda.itic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.AdminPermitidoRequestDto;
import com.agenda.itic.dto.AdminPermitidoResponseDto;
import com.agenda.itic.service.AdminPermitidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin-permitidos")
public class AdminPermitidoController {

    @Autowired
    AdminPermitidoService adminPermitidoService;

    @GetMapping
    public ResponseEntity<List<AdminPermitidoResponseDto>> getAll() {
        return ResponseEntity.ok(adminPermitidoService.getAllAdminPermitidos());
    }

    @PostMapping
    public ResponseEntity<AdminPermitidoResponseDto> create(@Valid @RequestBody AdminPermitidoRequestDto request) {
        return ResponseEntity.ok(adminPermitidoService.createAdminPermitido(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminPermitidoService.deleteAdminPermitido(id);
        return ResponseEntity.noContent().build();
    }
}
