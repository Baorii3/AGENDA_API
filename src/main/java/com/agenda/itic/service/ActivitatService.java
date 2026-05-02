package com.agenda.itic.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.agenda.itic.repository.UsuariRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.ActivitatRequestDTO;
import com.agenda.itic.dto.ActivitatResponseDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Activitat;
import com.agenda.itic.model.Sala;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.repository.ActivitatRepository;
import com.agenda.itic.repository.SalaRepository;

@Service
public class ActivitatService {


    @Autowired
    UsuariRepository usuariRepository;

    @Autowired
    ActivitatRepository activitatRepository;

    @Autowired
    SalaRepository salaRepository;


    public List<ActivitatResponseDTO> getAllActivitats() {
        return activitatRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private ActivitatResponseDTO toDTO(Activitat a) {
        return new ActivitatResponseDTO(
                a.getId_activitat(),
                a.getSala().getId(),
                a.getSala().getNom(),
                a.getUser().getId(),
                a.getUser().getNom(),
                a.getTitol(),
                a.getDescripcio(),
                a.getData(),
                a.getHoraInici(),
                a.getHoraFi(),
                a.isActiva());
    }

    private Activitat toModel(ActivitatRequestDTO activitatRequestDTO) {
        Activitat activitat = new Activitat();
        activitat.setSala(getSalaOrThrow(activitatRequestDTO.getId_sala()));
        activitat.setTitol(activitatRequestDTO.getTitol());
        activitat.setDescripcio(activitatRequestDTO.getDescripcio());
        activitat.setData(activitatRequestDTO.getData());
        activitat.setHoraInici(activitatRequestDTO.getHoraInici());
        activitat.setHoraFi(activitatRequestDTO.getHoraFi());
        activitat.setUser(getUsuariOrThrow(activitatRequestDTO.getId_usuari()));
        return activitat;
    }

    public ActivitatResponseDTO getActivitatById(Long id) {
        return toDTO(activitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activitat no trobada")));
    }

    public List<ActivitatResponseDTO> getActivitatsByUsuari(Long idUsuari) {
        return activitatRepository.findByUserId(idUsuari)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ActivitatResponseDTO createActivitat(ActivitatRequestDTO activitatRequestDTO) {
        validateActivitatRequest(activitatRequestDTO);
        Activitat activitat = toModel(activitatRequestDTO);
        activitat = activitatRepository.save(activitat);
        return toDTO(activitat);
    }

    public void deleteActivitat(Long id) {
        Activitat activitat = activitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activitat no trobada"));


        activitatRepository.delete(activitat);
    }

    private Usuari getUsuariOrThrow(Long idUsuari) {
        return usuariRepository.findById(idUsuari)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede crear la actividad: El usuario con ID " + idUsuari + " no existe."));
    }

    private Sala getSalaOrThrow(Long idSala) {
        return salaRepository.findById(idSala)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede crear la actividad: La sala con ID " + idSala + " no existe."));
    }

    private void validateActivitatRequest(ActivitatRequestDTO activitatRequestDTO) {
        if (activitatRequestDTO.getId_sala() == null || activitatRequestDTO.getId_usuari() == null) {
            throw new BadRequestException("Los IDs de la sala y el usuario son obligatorios.");
        }

        LocalTime horaInici = activitatRequestDTO.getHoraInici();
        LocalTime horaFi = activitatRequestDTO.getHoraFi();
        LocalDate data = activitatRequestDTO.getData();

        if (horaInici.getMinute() % 15 != 0) {
            throw new BadRequestException("La hora de inicio debe empezar en un cuarto de hora.");
        }

        if (!horaFi.isAfter(horaInici)) {
            throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin.");
        }

        long duracionMinutos = Duration.between(horaInici, horaFi).toMinutes();
        if (duracionMinutos > 120) {
            throw new BadRequestException("La actividad no puede durar más de 2 horas.");
        }

        if (data.isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha de la actividad no puede ser anterior a la fecha actual.");
        }

        if (data.isEqual(LocalDate.now()) && horaInici.isBefore(LocalTime.now())) {
            throw new BadRequestException("La hora de inicio de la actividad no puede ser anterior a la hora actual.");
        }

        // Comprueba si la sala ya tiene otra actividad que se solape en este horario.
        if (activitatRepository.existsBySalaIdAndDataAndHoraIniciLessThanAndHoraFiGreaterThanAndActivaTrue(
                activitatRequestDTO.getId_sala(),
                data,
                horaFi,
                horaInici)) {
            // Si hay cruce de horas, no se puede reservar la sala otra vez.
            throw new BadRequestException("La sala ya está ocupada en ese horario.");
        }
    }

    

    public List<Activitat> getActivitatModel() {
        return activitatRepository.findAll();
    }
}