package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.SalaRequest;
import com.agenda.itic.model.Sala;
import com.agenda.itic.repository.SalaRepository;

@Service
public class SalaService {

    @Autowired
    SalaRepository salaRepository;

    public List<Sala> getAllSalas() {
        return salaRepository.findAll();
    }

    public Sala postSala(SalaRequest salaRequest) {
        Sala sala = new Sala();
        sala.setNom_sala(salaRequest.getNom_sala());
        sala.setUbicacio_sala(salaRequest.getUbicacio_sala());
        sala.setDescripcio(salaRequest.getDescripcio());
        return salaRepository.save(sala);
    }

    public Sala putSala(Long id, SalaRequest salaRequest) {
        Sala sala = salaRepository.findById(id).orElse(null);
        if (sala != null) {
            sala.setNom_sala(salaRequest.getNom_sala());
            sala.setUbicacio_sala(salaRequest.getUbicacio_sala());
            sala.setDescripcio(salaRequest.getDescripcio());
            return salaRepository.save(sala);
            
        }
        return null;
    }

    public boolean deleteSala(Long id) {
        if (!salaRepository.existsById(id)) {
            return false;
        }
        salaRepository.deleteById(id);
        return true;
        
    }
}
