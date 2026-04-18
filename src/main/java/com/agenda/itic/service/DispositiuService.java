package com.agenda.itic.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.DispositiuRequestDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Dispositiu;
import com.agenda.itic.repository.DispositiuRepository;
import com.agenda.itic.repository.UsuariRepository;

@Service
public class DispositiuService {

    private final UsuariRepository usuariRepository;

    @Autowired
    DispositiuRepository dispositiuRepository;

    DispositiuService(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    public Dispositiu mapToDispositiu(DispositiuRequestDTO dispositiuDTO) {
        Dispositiu dispositiu = new Dispositiu();
        dispositiu.setNom(dispositiuDTO.getNom());
        dispositiu.setIp(dispositiuDTO.getIp());
        dispositiu.setMac(dispositiuDTO.getMac());
        dispositiu.setTipus(dispositiuDTO.getTipus());        
        dispositiu.setActiu(true);
        dispositiu.setHeartbeat(LocalDateTime.now());
        return dispositiu;
    }

    public List<Dispositiu> getDispositius() {
        return dispositiuRepository.findAll();
    }

    public Dispositiu createDispositiu(DispositiuRequestDTO dispositiu) {
        if (dispositiu == null) {
            throw new BadRequestException("DispositiuRequestDTO no pot ser null");
        }
        return dispositiuRepository.save(mapToDispositiu(dispositiu));
        
    }

    public Dispositiu updateDispositiu(Long id, DispositiuRequestDTO dispositiuDTO) {
        if (dispositiuDTO == null) {
            throw new BadRequestException("DispositiuRequestDTO no pot ser null");
        }
        Dispositiu existingDispositiu = dispositiuRepository.findById(id).orElse(null);
        if (existingDispositiu == null) {
            throw new ResourceNotFoundException("Dispositiu no trobat");
        }
        existingDispositiu = mapToDispositiu(dispositiuDTO);
        existingDispositiu.setId_dispositiu(id);
        return dispositiuRepository.save(existingDispositiu);
    }

    public void deleteDispositiu(Long id) {
        dispositiuRepository.deleteById(id);
    }
}
