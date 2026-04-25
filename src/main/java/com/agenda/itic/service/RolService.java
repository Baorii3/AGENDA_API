package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.RolRequestDTO;
import com.agenda.itic.dto.RolResponseDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Rol;
import com.agenda.itic.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    private RolResponseDTO toDTO(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getPermisos() == null ? List.of() : rol.getPermisos().stream().map(com.agenda.itic.model.Permis::getId).toList());
    }

    public List<RolResponseDTO> getAllRoles() {
        return rolRepository.findAll().stream().map(this::toDTO).toList();
    }

    public Rol getRolByName(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nom del rol no pot estar buit");
        }
        String normalizedName = nombre.trim().toUpperCase();
        return rolRepository.findByNombreIgnoreCase(normalizedName)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no trobat: " + normalizedName));
    }

    public Rol getOrCreateRol(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nom del rol no pot estar buit");
        }
        String normalizedName = nombre.trim().toUpperCase();
        Rol rol = rolRepository.findByNombreIgnoreCase(normalizedName).orElseThrow(() -> new ResourceNotFoundException("Rol no trobat: " + normalizedName))   ;
        if (rol == null) {
            rol = new Rol();
            rol.setNombre(normalizedName);
            rol = rolRepository.save(rol);
        }
        return rol;
    }

    public RolResponseDTO getRolById(Long id) {
        return toDTO(rolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol no trobat")));
    }

    public RolResponseDTO createRol(RolRequestDTO request) {
        if (request == null || request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nom del rol no pot estar buit");
        }
        String nombre = request.getNombre().trim().toUpperCase();
        rolRepository.findByNombreIgnoreCase(nombre).ifPresent(existing -> {
            throw new BadRequestException("Rol ja registrat");
        });

        Rol rol = new Rol();
        rol.setNombre(nombre);
        return toDTO(rolRepository.save(rol));
    }

    public RolResponseDTO updateRol(Long id, RolRequestDTO request) {
        Rol rol = rolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol no trobat"));
        if (request == null || request.getNombre() == null || request.getNombre().isBlank()) {
            throw new BadRequestException("El nom del rol no pot estar buit");
        }

        String nombre = request.getNombre().trim().toUpperCase();
        rolRepository.findByNombreIgnoreCase(nombre).ifPresent(existing -> {
            if (existing.getId() != id) {
                throw new BadRequestException("Rol ja registrat");
            }
        });

        rol.setNombre(nombre);
        return toDTO(rolRepository.save(rol));
    }

    public void deleteRol(Long id) {
        Rol rol = rolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol no trobat"));
        rolRepository.delete(rol);
    }
}