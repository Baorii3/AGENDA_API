package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.ActivitatRequestDTO;
import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.repository.ActivitatRepository;
import com.agenda.itic.repository.SalaRepository;

@Service
public class ActivitatService {

    @Autowired
    ActivitatRepository activitatRepository;

    @Autowired
    SalaRepository salaRepository;


    public List<ActivitatResponseDTO> getAllActivitats() {
        return activitatRepository.findAll()
                .stream()
                .map(act -> toDTO(act))
                .toList();
    }

    private ActivitatResponseDTO toDTO(Activitat a) {
        return new ActivitatResponseDTO(
                a.getId_activitat(),
                a.getSala().getId(),
                a.getSala().getNom(),
                a.getTitol(),
                a.getDescripcio(),
                a.getData(),
                a.getHoraInici(),
                a.getHoraFi(),
                a.isActiva());
    }

    private Activitat toModel(ActivitatRequestDTO activitatRequestDTO) {
        Activitat activitat = new Activitat();
        activitat.setSala(salaRepository.findById(activitatRequestDTO.getId_sala()).orElseThrow(() -> new ResourceNotFoundException("Sala no trobada")));
        activitat.setTitol(activitatRequestDTO.getTitol());
        activitat.setDescripcio(activitatRequestDTO.getDescripcio());
        activitat.setData(activitatRequestDTO.getData());
        activitat.setHoraInici(activitatRequestDTO.getHoraInici());
        activitat.setHoraFi(activitatRequestDTO.getHoraFi());
        return activitat;
    }

    public ActivitatResponseDTO getActivitatById(Long id) {
        return toDTO(activitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activitat no trobada")));
    }

    public ActivitatResponseDTO createActivitat(ActivitatRequestDTO activitatRequestDTO) {
        if (!salaRepository.existsById(activitatRequestDTO.getId_sala())) {
            throw new ResourceNotFoundException("No se puede crear la actividad: La sala con ID " 
            + activitatRequestDTO.getId_sala() + " no existe.");
        }
        Activitat activitat = toModel(activitatRequestDTO);
        activitat = activitatRepository.save(activitat);
        return toDTO(activitat);
    }

    public void deleteActivitat(Long id) {
        Activitat activitat = activitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activitat no trobada"));
        activitatRepository.delete(activitat);
    }

    public List<Activitat> getActivitatModel() {
        return activitatRepository.findAll();
    }
}