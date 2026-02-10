package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.model.Dispositiu;
import com.agenda.itic.repository.DispositiuRepository;

@Service
public class DispositiuService {

    @Autowired
    DispositiuRepository dispositiuRepository;

    public List<Dispositiu> getDispositius() {
        return dispositiuRepository.findAll();
    }

    public Dispositiu createDispositiu(Dispositiu dispositiu) {
        if (dispositiu == null) {
            return null;
        }
        try {
            return dispositiuRepository.save(dispositiu);
        } catch (Exception e) {
            return null;
        }
    }

    public Dispositiu updateDispositiu(Long id, Dispositiu dispositiu) {
        if (dispositiu == null) {
            return null;
        }
        try {
            Dispositiu existingDispositiu = dispositiuRepository.findById(id).orElse(null);
            if (existingDispositiu != null) {
                dispositiu.setId_dispositiu(id);
                return dispositiuRepository.save(dispositiu);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteDispositiu(Long id) {
        try {
            dispositiuRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
