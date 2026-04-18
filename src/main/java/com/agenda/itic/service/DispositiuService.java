package com.agenda.itic.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.DispositiuRequestDTO;
import com.agenda.itic.dto.DispositiuResponseDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Dispositiu;
import com.agenda.itic.repository.DispositiuRepository;

@Service
public class DispositiuService {


    @Autowired
    DispositiuRepository dispositiuRepository;

    public Dispositiu mapToDispositiu(DispositiuRequestDTO dispositiuDTO) {
        Dispositiu dispositiu = new Dispositiu();
        dispositiu.setNom(dispositiuDTO.getNom());
        dispositiu.setIp(dispositiuDTO.getIp());
        dispositiu.setMac(dispositiuDTO.getMac());
        dispositiu.setTipus(dispositiuDTO.getTipus());        
        dispositiu.setActiu(dispositiuDTO.getActiu());
        dispositiu.setHeartbeat(LocalDateTime.now());
        return dispositiu;
    }

    public DispositiuResponseDTO mapToResponseDTO(Dispositiu dispositiu) {
        return new DispositiuResponseDTO(
            dispositiu.getId_dispositiu(),
            dispositiu.getNom(),
            dispositiu.getMac(),
            dispositiu.getIp(),
            dispositiu.isActiu(),
            dispositiu.getDataCreacio(),
            dispositiu.getHeartbeat()
        );
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
