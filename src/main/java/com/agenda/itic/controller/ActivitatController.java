package com.agenda.itic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.service.ActivitatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/activitats")
public class ActivitatController {

    @Autowired
    ActivitatService activitatService;

    @GetMapping("/activitats")
    public ResponseEntity<List<ActivitatResponseDTO>> getAllActivitats() {
        return ResponseEntity.status(HttpStatus.OK).body(activitatService.getAllActivitats());
    }
    
}
