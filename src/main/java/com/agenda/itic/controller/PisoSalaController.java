package com.agenda.itic.controller;

import com.agenda.itic.model.PisoSala;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pisos")
@CrossOrigin(origins = "*")
public class PisoSalaController {

    @GetMapping
    public List<PisoSala> getAllPisos() {
        return Arrays.asList(PisoSala.values());
    }
}
