package com.agenda.itic.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.UsuariResponseDto;
import com.agenda.itic.dto.UsuariTokenDto;
import com.agenda.itic.dto.PermisUsuariDto;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.Permis;
import com.agenda.itic.model.Rol;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.repository.UsuariRepository;

@Service
public class UsuariService {

    @Autowired
    UsuariRepository usuariRepository;

    @Autowired
    AdminPermitidoService adminPermitidoService;

    @Autowired
    RolService rolService;

    private UsuariResponseDto toDTO(Usuari usuari) {
        return new UsuariResponseDto(
                usuari.getId(),
                usuari.getNom(),
                usuari.getEmail(),
                usuari.getRol() == null ? null : usuari.getRol().getNombre(),
                usuari.getRol() == null || usuari.getRol().getPermisos() == null
                        ? List.of()
                        : usuari.getRol().getPermisos().stream().map(this::toPermisDto).toList(),
                usuari.getFotoPerfil());
    }

    private PermisUsuariDto toPermisDto(Permis permis) {
        return new PermisUsuariDto(permis.getRecurso().getNombre().toString(), permis.getValueAccio());
    }

    public List<UsuariResponseDto> getUsuaris() {
        return usuariRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<UsuariResponseDto> getUsuarisActius(boolean actiu) {
        return usuariRepository.findByActiu(actiu).stream().map(this::toDTO).toList();
    }

    public UsuariResponseDto getUsuariById(Long id) {
        return toDTO(usuariRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuari no trobat")));
    }

    public List<UsuariResponseDto> getUsuarisProfes() {
        Rol professor = rolService.getRolByName("PROFESSOR");
        return usuariRepository.findByRol(professor).stream().map(this::toDTO).toList();
    }

    private Usuari mapToUsuari(UsuariTokenDto usuariRequestDTO) {
        Usuari usuari = new Usuari();
        usuari.setNom(usuariRequestDTO.getNom());
        usuari.setEmail(usuariRequestDTO.getEmail());
        usuari.setRol(getRol(usuariRequestDTO.getEmail()));
        usuari.setActiu(true);
        usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : "local");
        usuari.setProviderId(usuariRequestDTO.getProviderId());
        usuari.setFotoPerfil(usuariRequestDTO.getFotoPerfil());
        return usuari;
    }

    private Rol getRol(String email) {
        String rolName;
        if (!email.contains("@iticbcn.cat")) {
            throw new BadRequestException("Només s'accepten correus de l'ITIC BCN.");
        }

        try {
            if (email.equalsIgnoreCase("2223_ian.ordonez@iticbcn.cat")
                    || adminPermitidoService.getAdminPermitido(email) != null) {
                rolName = "ADMIN";
                return getOrCreateRol(rolName);
            }
        } catch (ResourceNotFoundException e) {
        }

        if (!email.split("@")[0].contains("_")) {
            rolName = "PROFESSOR";
            return getOrCreateRol(rolName);
        }

        rolName = "USUARI";
        return getOrCreateRol(rolName);
    }

    private Rol getOrCreateRol(String nombre) {
        return rolService.getOrCreateRol(nombre);
    }

    public UsuariResponseDto createUsuari(UsuariTokenDto usuariRequestDTO) {
        if (usuariRepository.findByEmail(usuariRequestDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Ya existe un usuario con ese correo");
        }

        Rol rol = getRol(usuariRequestDTO.getEmail());
        Usuari usuari = mapToUsuari(usuariRequestDTO);
        usuari.setRol(rol);
        return toDTO(usuariRepository.save(usuari));
    }

    public UsuariResponseDto createOrUpdateUsuariFromToken(Jwt jwt) {
        if (jwt == null) {
            throw new BadRequestException("Token inválido");
        }

        String email = normalizeEmail((String) jwt.getClaim("email"));
        if (email == null) {
            throw new BadRequestException("Token inválido: falta el claim email");
        }

        Usuari user = usuariRepository.findByEmail(email).orElse(new Usuari());

        user.setEmail(email);
        String name = jwt.getClaim("name");
        user.setNom(name == null ? "Desconocido" : name);
        String picture = jwt.getClaim("picture");
        user.setFotoPerfil(picture);

        Rol rol = getRol(email);
        user.setRol(rol);
        user.setActiu(true);
        user.setProvider("google");
        String providerId = jwt.getClaim("sub");
        user.setProviderId(providerId);
        user = usuariRepository.save(user);

        System.out.println("DEBUG: User processed. Email: " + email + ", Role: "
                + (user.getRol() != null ? user.getRol().getNombre() : "NULL"));

        return toDTO(user);
    }

    public UsuariResponseDto updateUsuari(Long id, UsuariTokenDto usuariRequestDTO) {
        Usuari usuari = usuariRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuari no trobat"));

        usuariRepository.findByEmail(usuariRequestDTO.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BadRequestException("Ya existe un usuario con ese correo");
                });

        usuari.setNom(usuariRequestDTO.getNom());
        usuari.setEmail(usuariRequestDTO.getEmail());
        usuari.setRol(getRol(usuariRequestDTO.getEmail()));
        usuari.setActiu(true);
        usuari.setProvider(
                usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : usuari.getProvider());
        usuari.setProviderId(usuariRequestDTO.getProviderId() != null ? usuariRequestDTO.getProviderId()
                : usuari.getProviderId());
        if (usuariRequestDTO.getFotoPerfil() != null) {
            usuari.setFotoPerfil(usuariRequestDTO.getFotoPerfil());
        }
        return toDTO(usuariRepository.save(usuari));
    }

    public void deleteUsuari(Long id) {
        Usuari usuari = usuariRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuari no trobat"));
        usuariRepository.delete(usuari);
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isBlank() ? null : normalized;
    }
}
