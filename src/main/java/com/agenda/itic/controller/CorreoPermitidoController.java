package com.agenda.itic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.service.CorreoPermitidoService;
import com.agenda.itic.model.CorreoPermitido;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/correos-permitidos")
public class CorreoPermitidoController {

    @Autowired
    CorreoPermitidoService correoPermitidoService;

    @GetMapping("/correos-permitidos") 
    public ResponseEntity<List<CorreoPermitido>> getAllCorreosPermitidos() {
        return ResponseEntity.ok(correoPermitidoService.getAllCorreosPermitidos());
    }

    @GetMapping("/correos-permitidos/{email}")
    public ResponseEntity<CorreoPermitido> getCorreoPermitido(@PathVariable String email) {
        return ResponseEntity.ok(correoPermitidoService.getCorreoPermitido(email));
    }

    @PostMapping("/correos-permitidos")
    public ResponseEntity<CorreoPermitido> createCorreoPermitido(@RequestBody CorreoPermitido correoPermitido) {
        CorreoPermitido createdCorreoPermitido = correoPermitidoService.createCorreoPermitido(correoPermitido);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCorreoPermitido);
    }

}