package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.repository.ActivitatRepository;

@Service
public class ActivitatService {

    @Autowired
    ActivitatRepository activitatRepository;

    public List<ActivitatResponseDTO> getAllActivitats() {
        return activitatRepository.findAll()
                .stream()
                .map(user -> toDTO(user))
                .toList();
    }
    private ActivitatResponseDTO toDTO(Activitat a) {
        return new ActivitatResponseDTO(
            a.getSala().getId(),
            a.getTitol(),
            a.getResum(),
            a.getDescripcio(),
            a.getUser().getId(),
            a.getData(),
            a.getHoraInici(),
            a.getHoraFi(),
            a.getPrioritat(),
            a.getEstat().name(),
            a.getVisible()
        );
    }
}
