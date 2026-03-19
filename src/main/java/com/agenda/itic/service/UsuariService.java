package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.UsuariRequestDTO;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.model.Usuari.Rol;
import com.agenda.itic.repository.UsuariRepository;

@Service
public class UsuariService {

    @Autowired
    UsuariRepository usuariRepository;

    @Autowired
    CorreoPermitidoService correoPermitidoService;

    public List<Usuari> getUsuaris() {
        return usuariRepository.findAll();
    }

    public List<Usuari> getUsuarisActius(Boolean actiu) {
        return usuariRepository.findByActiu(actiu);
    }

    public Usuari createUsuari(UsuariRequestDTO usuariRequestDTO) {
        if (correoPermitidoService.getCorreoPermitido(usuariRequestDTO.getEmail()) == null) {
            return null;
        }
        try {
            Usuari usuari = new Usuari();
            usuari.setNom(usuariRequestDTO.getNom());
            usuari.setEmail(usuariRequestDTO.getEmail());
            usuari.setRol(usuariRequestDTO.getRol() != null ? usuariRequestDTO.getRol() : Rol.usuari);
            usuari.setActiu(usuariRequestDTO.getActiu() != null ? usuariRequestDTO.getActiu() : false);
            usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : "local");
            usuari.setProviderId(usuariRequestDTO.getProviderId());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
        }
    }

    public Usuari createOrUpdateOAuthUsuari(UsuariRequestDTO usuariRequestDTO) {
        if (usuariRequestDTO == null || usuariRequestDTO.getEmail() == null) {
            return null;
        }
        try {
            Usuari usuari = usuariRepository.findByEmail(usuariRequestDTO.getEmail()).orElseGet(Usuari::new);
            if (usuari.getId() == null) {
                usuari.setNom(usuariRequestDTO.getNom());
                usuari.setEmail(usuariRequestDTO.getEmail());
                usuari.setRol(Rol.usuari);
                usuari.setActiu(true);
                usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : "local");
                usuari.setProviderId(usuariRequestDTO.getProviderId());
            }
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
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
            usuari.setRol(usuariRequestDTO.getRol() != null ? usuariRequestDTO.getRol() : Rol.usuari);
            usuari.setActiu(usuariRequestDTO.getActiu() != null ? usuariRequestDTO.getActiu() : false);
            usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : usuari.getProvider());
            usuari.setProviderId(usuariRequestDTO.getProviderId() != null ? usuariRequestDTO.getProviderId() : usuari.getProviderId());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteUsuari(Long id) {
        if (!usuariRepository.existsById(id)) {
            return false;
        }
        
        try {
            usuariRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
