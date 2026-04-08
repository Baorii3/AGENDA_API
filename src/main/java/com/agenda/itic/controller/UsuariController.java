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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.UsuariRequestDTO;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.service.UsuariService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/usuaris")
@CrossOrigin(origins = "*")
public class UsuariController {

    @Autowired
    UsuariService usuariService;

    @GetMapping("/usuaris")
    public ResponseEntity<List<Usuari>> getUsuaris() {
        List<Usuari> usuaris = usuariService.getUsuaris();
        return ResponseEntity.status(HttpStatus.OK).body(usuaris);
    }

    @GetMapping("/usuaris/{actiu}")
    public ResponseEntity<List<Usuari>> getUsuarisActius(Boolean actiu) {
        List<Usuari> usuaris = usuariService.getUsuarisActius(actiu);
        return ResponseEntity.status(HttpStatus.OK).body(usuaris);
    }

    @PostMapping("/usuaris")
    public ResponseEntity<Usuari> createUsuari(@RequestBody UsuariRequestDTO usuari) {
        Usuari createdUsuari = usuariService.createUsuari(usuari);
        if (createdUsuari == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuari);
    }

    @PutMapping("/usuaris/{id}")
    public ResponseEntity<Usuari> updateUsuari(@PathVariable Long id, @RequestBody UsuariRequestDTO usuariRequestDTO) {
        Usuari updatedUsuari = usuariService.updateUsuari(id, usuariRequestDTO);
        if (updatedUsuari == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedUsuari);
    }

    @DeleteMapping("/usuaris/{id}")
    public ResponseEntity<Void> deleteUsuari(@PathVariable Long id) {
        boolean deleted = usuariService.deleteUsuari(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/token")
    public ResponseEntity<?> createOrUpdateUsuariFromToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization inválido. Debe empezar por Bearer");
        }

        String token = authHeader.substring(7).trim();
        if (token.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El token está vacío");
        }

        Usuari createdOrUpdateUsuari = usuariService.createOrUpdateUsuariFromToken(token);
        if (createdOrUpdateUsuari == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear o actualizar el usuario");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrUpdateUsuari);
    }
}
