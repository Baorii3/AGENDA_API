package com.agenda.itic.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.dto.AdminPermitidoRequestDto;
import com.agenda.itic.dto.AdminPermitidoResponseDto;
import com.agenda.itic.exception.BadRequestException;
import com.agenda.itic.exception.ResourceNotFoundException;
import com.agenda.itic.model.AdminPermitido;
import com.agenda.itic.repository.AdminPermitidoRepository;

@Service
public class AdminPermitidoService {

    @Autowired
    AdminPermitidoRepository adminPermitidoRepository;

    private AdminPermitidoResponseDto toDTO(AdminPermitido adminPermitido) {
        return new AdminPermitidoResponseDto(adminPermitido.getId(), adminPermitido.getCorreo());
    }

    public List<AdminPermitidoResponseDto> getAllAdminPermitidos() {
        return adminPermitidoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public AdminPermitidoResponseDto getAdminPermitido(String email) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null) {
            throw new BadRequestException("Correo inválido");
        }
        AdminPermitido adminPermitido = adminPermitidoRepository.findByCorreoIgnoreCase(normalizedEmail);
        if (adminPermitido == null) {
            throw new ResourceNotFoundException("Correo no encontrado: " + email);
        }
        return toDTO(adminPermitido);
    }

    public AdminPermitidoResponseDto createAdminPermitido(AdminPermitidoRequestDto request) {
        String normalizedEmail = normalizeEmail(request.correo());
        if (normalizedEmail == null) {
            throw new BadRequestException("Correo inválido");
        }

        if (adminPermitidoRepository.findByCorreoIgnoreCase(normalizedEmail) != null) {
            throw new BadRequestException("Correo ya registrado");
        }
        return toDTO(adminPermitidoRepository.save(new AdminPermitido(normalizedEmail)));
    }

    public void deleteAdminPermitido(Long id) {
        AdminPermitido adminPermitido = adminPermitidoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Correo no encontrado con id: " + id));
        adminPermitidoRepository.delete(adminPermitido);
    }

    private String normalizeEmail(String email) {
        if (email == null)
            return null;
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isEmpty() ? null : normalized;
    }
}
