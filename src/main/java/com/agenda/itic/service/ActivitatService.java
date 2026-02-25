package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.ActivitatRequestDTO;
import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.repository.ActivitatRepository;
import com.agenda.itic.repository.SalaRepository;
import com.agenda.itic.repository.UsuariRepository;

@Service
public class ActivitatService {

    @Autowired
    ActivitatRepository activitatRepository;

    @Autowired
    SalaRepository salaRepository;

    @Autowired
    UsuariRepository usuariRepository;

    @Autowired
    GoogleCalendarService googleCalendarService;

    public List<ActivitatResponseDTO> getAllActivitats() {
        return activitatRepository.findAll()
                .stream()
                .map(user -> toDTO(user))
                .toList();
    }

    private ActivitatResponseDTO toDTO(Activitat a) {
        return new ActivitatResponseDTO(
                a.getId_sala(),
                a.getGoogleId(),
                a.getTitol(),
                a.getResum(),
                a.getDescripcio(),
                a.getData(),
                a.getHoraInici(),
                a.getHoraFi(),
                a.getEstat().name(),
                a.getVisible());
    }

    private Activitat toModel(ActivitatRequestDTO activitatRequestDTO) {
        Activitat activitat = new Activitat();
        activitat.setId_sala(activitatRequestDTO.getId_sala());
        activitat.setTitol(activitatRequestDTO.getTitol());
        activitat.setResum(activitatRequestDTO.getResum());
        activitat.setDescripcio(activitatRequestDTO.getDescripcio());
        activitat.setData(activitatRequestDTO.getData());
        activitat.setHoraInici(activitatRequestDTO.getHoraInici());
        activitat.setHoraFi(activitatRequestDTO.getHoraFi());
        activitat.setEstat(activitatRequestDTO.getEstat());
        activitat.setVisible(activitatRequestDTO.getVisible());

        return activitat;
    }

    public ActivitatResponseDTO getActivitatById(Long id) {
        return toDTO(activitatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activitat no trobada")));
    }

    public ActivitatResponseDTO createActivitat(ActivitatRequestDTO activitatRequestDTO) {
        if (activitatRequestDTO.getTitol() == null) {
            throw new IllegalArgumentException("El títol és obligatori");
        }
        if (activitatRequestDTO.getResum() == null) {
            throw new IllegalArgumentException("El resum és obligatori");
        }
        if (activitatRequestDTO.getDescripcio() == null) {
            throw new IllegalArgumentException("La descripció és obligatòria");
        }
        Activitat activitat = toModel(activitatRequestDTO);

        try {
            activitat = activitatRepository.save(activitat);
            String googleId = googleCalendarService.addEvent(activitat);
            if (googleId != null && !googleId.isBlank()) {
                activitat.setGoogleId(googleId);
                activitat = activitatRepository.save(activitat);
            }
            return toDTO(activitat);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear l'activitat", e);
        }
    }

    public boolean deleteActivitat(Long id) {
        try {
            Activitat activitat = activitatRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Activitat no trobada"));
            googleCalendarService.deleteEvent(activitat.getGoogleId());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar l'activitat", e);
        }
    }

    public List<Activitat> getActivitatModel() {
        return activitatRepository.findAll();
    }
}