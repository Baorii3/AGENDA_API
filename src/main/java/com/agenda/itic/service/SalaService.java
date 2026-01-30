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
        if (salaRequest == null) {
            return null;
        }
        try {
            Sala sala = new Sala();
            sala.setNom(salaRequest.getNom());
            sala.setTipus(salaRequest.getTipus());
            sala.setUbicacio(salaRequest.getUbicacio());
            sala.setDescripcio(salaRequest.getDescripcio());
            sala.setActiva(salaRequest.getActiva());
            return salaRepository.save(sala);
        } catch (Exception e) {
            return null;
        }
    }

    public Sala putSala(Long id, SalaRequest salaRequest) {
        if (salaRequest == null) {
            return null;
        }
        try {
            Sala sala = salaRepository.findById(id).orElse(null);
            if (sala != null) {
                sala.setNom(salaRequest.getNom());
                sala.setTipus(salaRequest.getTipus());
                sala.setUbicacio(salaRequest.getUbicacio());
                sala.setDescripcio(salaRequest.getDescripcio());
                sala.setActiva(salaRequest.getActiva());
                return salaRepository.save(sala);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteSala(Long id) {
        if (!salaRepository.existsById(id)) {
            return false;
        }
        try {
            salaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}