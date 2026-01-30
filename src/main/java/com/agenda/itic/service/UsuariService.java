package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.UsuariRequestDTO;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.repository.UsuariRepository;

@Service
public class UsuariService {

    @Autowired
    UsuariRepository usuariRepository;

    public List<Usuari> getUsuaris() {
        return usuariRepository.findAll();
    }

    public List<Usuari> getUsuarisActius(Boolean actiu) {
        return usuariRepository.findByActiu(actiu);
    }

    public Usuari createUsuari(UsuariRequestDTO usuariRequestDTO) {
        if (usuariRequestDTO == null) {
            return null;
        }
        try {
            Usuari usuari = new Usuari();
            usuari.setNom(usuariRequestDTO.getNom());
            usuari.setEmail(usuariRequestDTO.getEmail());
            usuari.setRol(usuariRequestDTO.getRol());
            usuari.setActiu(usuariRequestDTO.getActiu());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            return null;
        }
    }

    public Usuari updateUsuari(Long id, UsuariRequestDTO usuariRequestDTO) {
        if (usuariRequestDTO == null) {
            return null;
        }
        try {
            Usuari usuari = usuariRepository.findById(id).get();
            usuari.setNom(usuariRequestDTO.getNom());
            usuari.setEmail(usuariRequestDTO.getEmail());
            usuari.setRol(usuariRequestDTO.getRol());
            usuari.setActiu(usuariRequestDTO.getActiu());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            return null;
        }
    }

    public Usuari deleteUsuari(Long id) {
        try {
            Usuari usuari = usuariRepository.findById(id).get();
            usuariRepository.delete(usuari);
            return usuari;
        } catch (Exception e) {
            return null;
        }
    }
}
