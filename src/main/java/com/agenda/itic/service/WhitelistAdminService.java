package com.agenda.itic.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.WhitelistAdminRequestDto;
import com.agenda.itic.dto.WhitelistAdminResponseDto;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.WhitelistAdmin;
import com.agenda.itic.repository.WhitelistAdminRepository;

@Service
public class WhitelistAdminService {

    @Autowired
    WhitelistAdminRepository whitelistAdminRepository;

    private WhitelistAdminResponseDto toDTO(WhitelistAdmin whitelistAdmin) {
        return new WhitelistAdminResponseDto(whitelistAdmin.getId(), whitelistAdmin.getCorreo());
    }

    public List<WhitelistAdminResponseDto> getAllWhitelistAdmins() {
        return whitelistAdminRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public WhitelistAdminResponseDto getWhitelistAdmin(String email) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null) {
            throw new BadRequestException("Correo inválido");
        }
        WhitelistAdmin whitelistAdmin = whitelistAdminRepository.findByCorreoIgnoreCase(normalizedEmail);
        if (whitelistAdmin == null) {
            throw new ResourceNotFoundException("Correo no encontrado: " + email);
        }
        return toDTO(whitelistAdmin);
    }

    public WhitelistAdminResponseDto createWhitelistAdmin(WhitelistAdminRequestDto request) {
        String normalizedEmail = normalizeEmail(request.getCorreo());
        if (normalizedEmail == null) {
            throw new BadRequestException("Correo inválido");
        }

        if (whitelistAdminRepository.findByCorreoIgnoreCase(normalizedEmail) != null) {
            throw new BadRequestException("Correo ya registrado");
        }
        return toDTO(whitelistAdminRepository.save(new WhitelistAdmin(normalizedEmail)));
    }

    public void deleteWhitelistAdmin(Long id) {
        WhitelistAdmin whitelistAdmin = whitelistAdminRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Correo no encontrado con id: " + id)
        );
        whitelistAdminRepository.delete(whitelistAdmin);
    }

    private String normalizeEmail(String email) {
        if (email == null) return null;
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isEmpty() ? null : normalized;
    }
}
