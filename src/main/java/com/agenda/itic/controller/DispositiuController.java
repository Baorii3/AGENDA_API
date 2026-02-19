package com.agenda.itic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.DispositiuRequestDTO;
import com.agenda.itic.model.Dispositiu;
import com.agenda.itic.service.DispositiuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/dispositius")
@CrossOrigin(origins = "*")
public class DispositiuController {

    @Autowired
    DispositiuService dispositiuService;

    @GetMapping("/dispositius")
    public ResponseEntity<List<Dispositiu>> getAllDispositius() {
        return ResponseEntity.status(HttpStatus.OK).body(dispositiuService.getDispositius());
    }

    @PostMapping("/dispositius")
    public ResponseEntity<List<Dispositiu>> postMethodName(@RequestBody DispositiuRequestDTO dispositiu) {
        Dispositiu entity = dispositiuService.createDispositiu(dispositiu);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(List.of(entity));
    }

    @PutMapping("/dispositius/{id}")
    public ResponseEntity<Dispositiu> putMethodName(@PathVariable Long id, @RequestBody DispositiuRequestDTO entity) {
        Dispositiu updatedEntity = dispositiuService.updateDispositiu(id, entity);
        if (updatedEntity == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedEntity);
    }

    @DeleteMapping("/dispositius/{id}")
    public ResponseEntity<Void> deleteDispositiu(@PathVariable Long id) {
        boolean deleted = dispositiuService.deleteDispositiu(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}