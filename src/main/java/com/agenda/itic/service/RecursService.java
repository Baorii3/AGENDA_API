package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.RecursRequestDTO;
import com.agenda.itic.dto.RecursResponseDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Recurs;
import com.agenda.itic.repository.RecursRepository;

@Service
public class RecursService {

    @Autowired
    RecursRepository recursRepository;

    private RecursResponseDTO toDTO(Recurs recurs) {
        return new RecursResponseDTO(recurs.getId(), recurs.getNombre());
    }

    public List<RecursResponseDTO> getAllRecursos() {
        return recursRepository.findAll().stream().map(this::toDTO).toList();
    }

    public RecursResponseDTO getRecursById(Long id) {
        return toDTO(recursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurs no trobat")));
    }

    public RecursResponseDTO createRecurs(RecursRequestDTO request) {
        if (request == null || request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nom del recurs no pot ser buit");
        }
        String normalizedName = request.getNombre().trim();
        recursRepository.findByNombreIgnoreCase(normalizedName).ifPresent(existing -> {
            throw new BadRequestException("Recurs ja registrat");
        });

        Recurs recurs = new Recurs();
        recurs.setNombre(normalizedName);
        return toDTO(recursRepository.save(recurs));
    }

    public RecursResponseDTO updateRecurs(Long id, RecursRequestDTO request) {
        Recurs recurs = recursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurs no trobat"));

        if (request == null || request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nom del recurs no pot ser buit");
        }

        String normalizedName = request.getNombre().trim();
        recursRepository.findByNombreIgnoreCase(normalizedName).ifPresent(existing -> {
            if (existing.getId() != id) {
                throw new BadRequestException("Recurs ja registrat");
            }
        });

        recurs.setNombre(normalizedName);
        return toDTO(recursRepository.save(recurs));
    }

    public void deleteRecurs(Long id) {
        Recurs recurs = recursRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurs no trobat"));
        recursRepository.delete(recurs);
    }
}