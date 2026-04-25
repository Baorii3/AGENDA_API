package com.agenda.itic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.PermisRequestDTO;
import com.agenda.itic.dto.PermisResponseDTO;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Accio;
import com.agenda.itic.model.Permis;
import com.agenda.itic.model.Rol;
import com.agenda.itic.repository.PermisRepository;
import com.agenda.itic.repository.RecursRepository;
import com.agenda.itic.repository.RolRepository;

@Service
public class PermisService {

    @Autowired
    PermisRepository permisRepository;

    @Autowired
    RecursRepository recursRepository;

    @Autowired
    RolRepository rolRepository;

    private PermisResponseDTO toDTO(Permis permis) {
        return new PermisResponseDTO(
                permis.getId(),
                permis.getRecurso().getId(),
                permis.getRecurso().getNombre(),
                permis.getRol().getNombre(),
                permis.getValueAccio(),
                toAccions(permis.getValueAccio()));
    }

    private List<String> toAccions(int valueAccio) {
        return java.util.Arrays.stream(Accio.values())
                .filter(accio -> (valueAccio & accio.getValue()) != 0)
                .map(Enum::name)
                .toList();
    }

    public List<PermisResponseDTO> getAllPermisos() {
        return permisRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PermisResponseDTO getPermisById(Long id) {
        return toDTO(permisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permís no trobat")));
    }

    public PermisResponseDTO createPermis(PermisRequestDTO request) {
        validateRequest(request);
        ensureRecursExists(request.getIdRecurs());
        ensureRolExists(request.getIdRol());
        ensureUniquePair(request.getIdRol(), request.getIdRecurs(), null);

        Rol rol = rolRepository.findById(request.getIdRol())
            .orElseThrow(() -> new ResourceNotFoundException("Rol no trobat"));

        Permis permis = new Permis();
        permis.setRecurso(recursRepository.findById(request.getIdRecurs())
                .orElseThrow(() -> new ResourceNotFoundException("Recurs no trobat")));
        permis.setRol(rol);
        permis.setAcciones(request.getValueAccio());
        return toDTO(permisRepository.save(permis));
    }

    public PermisResponseDTO updatePermis(Long id, PermisRequestDTO request) {
        validateRequest(request);
        ensureRecursExists(request.getIdRecurs());
        ensureRolExists(request.getIdRol());

        Permis permis = permisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permís no trobat"));

        ensureUniquePair(request.getIdRol(), request.getIdRecurs(), id);

        Rol rol = rolRepository.findById(request.getIdRol())
            .orElseThrow(() -> new ResourceNotFoundException("Rol no trobat"));

        permis.setRecurso(recursRepository.findById(request.getIdRecurs())
                .orElseThrow(() -> new ResourceNotFoundException("Recurs no trobat")));
        permis.setRol(rol);
        permis.setAcciones(request.getValueAccio());
        return toDTO(permisRepository.save(permis));
    }

    public void deletePermis(Long id) {
        Permis permis = permisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permís no trobat"));
        permisRepository.delete(permis);
    }

    public List<PermisResponseDTO> getPermisosByRol(String rol) {
        return permisRepository.findByRolNombreIgnoreCase(rol)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<PermisResponseDTO> getPermisosByRecurs(Long idRecurs) {
        return permisRepository.findByRecursoId(idRecurs)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private void validateRequest(PermisRequestDTO request) {
        if (request == null) {
            throw new BadRequestException("PermisRequestDTO no pot ser null");
        }
        if (request.getValueAccio() < 0 || request.getValueAccio() > 15) {
            throw new BadRequestException("Permisos inválidos: debe ser entre 0 y 15");
        }
    }

    private void ensureRecursExists(Long idRecurs) {
        if (!recursRepository.existsById(idRecurs)) {
            throw new ResourceNotFoundException("Recurs no trobat");
        }
    }

    private void ensureRolExists(Long idRol) {
        if (!rolRepository.existsById(idRol)) {
            throw new ResourceNotFoundException("Rol no trobat");
        }
    }

    private void ensureUniquePair(Long idRol, Long idRecurs, Long currentId) {
        permisRepository.findByRolIdAndRecursoId(idRol, idRecurs).ifPresent(existing -> {
            if (currentId == null || existing.getId() != currentId) {
                throw new BadRequestException("Ja existeix un permís per aquest rol i recurs");
            }
        });
    }
}