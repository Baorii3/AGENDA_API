package com.agenda.itic.service;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.model.CorreoPermitido;
import com.agenda.itic.repository.CorreoPermitidoRepository;

@Service
public class CorreoPermitidoService {

    @Autowired
    CorreoPermitidoRepository correopermitidorepository;

    public List<CorreoPermitido> getAllCorreosPermitidos() {
        return correopermitidorepository.findAll();
    }

    public CorreoPermitido getCorreoPermitido(String email) {
        String normalizedEmail = normalizeEmail(email);
        if (normalizedEmail == null) {
            return null;
        }
        return correopermitidorepository.findByCorreoIgnoreCase(normalizedEmail);
    }


    public CorreoPermitido createCorreoPermitido(String correoPermitidoStr) {
        String normalizedEmail = normalizeEmail(correoPermitidoStr);
        if (normalizedEmail == null) {
            throw new RuntimeException("Correo inválido");
        }

        if (correopermitidorepository.findByCorreoIgnoreCase(normalizedEmail) != null) {
            throw new RuntimeException("Correo ya registrado");
        }
        try {
            return correopermitidorepository.save(new CorreoPermitido(normalizedEmail));
        } catch (Exception e) {
            throw new RuntimeException("Error creando un correo permitido", e);
        }

    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isEmpty() ? null : normalized;
    }

}