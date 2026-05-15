package com.agenda.itic.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.agenda.itic.model.Accio;
import com.agenda.itic.model.RecursNom;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.repository.UsuariRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    private final UsuariRepository usuariRepository;

    public SecurityService(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    public boolean hasPermission(RecursNom recurs, Accio accio) {
        if (recurs == null || accio == null) {
            return false;
        }

        Optional<Usuari> currentUsuari = getCurrentUsuari();
        if (currentUsuari.isEmpty() || currentUsuari.get().getRol() == null) {
            return false;
        }

        Usuari usuari = currentUsuari.get();
        if (isAdmin(usuari)) {
            return true;
        }

        if (usuari.getRol().getPermisos() == null) {
            return false;
        }

        return usuari.getRol().getPermisos().stream()
                .filter(p -> p.getRecurso() != null && p.getRecurso().getNombre() == recurs)
                .anyMatch(p -> (p.getValueAccio() & accio.getValue()) != 0);
    }

    public boolean hasPermission(String recursNom, String accioNom) {
        RecursNom recurs;
        Accio accio;

        try {
            recurs = RecursNom.valueOf(recursNom.toUpperCase(Locale.ROOT));
            accio = Accio.valueOf(accioNom.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return hasPermission(recurs, accio);
    }

    public boolean isAdmin() {
        return getCurrentUsuari().map(this::isAdmin).orElse(false);
    }

    private boolean isAdmin(Usuari usuari) {
        return usuari.getRol() != null && usuari.getRol().getNombre() != null
                && "ADMIN".equalsIgnoreCase(usuari.getRol().getNombre());
    }

    private Optional<Usuari> getCurrentUsuari() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return Optional.empty();
        }

        HttpServletRequest request = attrs.getRequest();
        String email = extractEmailFromAuthorizationHeader(request.getHeader("Authorization"));

        if (email == null || email.isBlank()) {
            email = normalizeEmail(request.getHeader("X-User-Email"));
        }

        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return usuariRepository.findByEmail(email);
    }

    private String extractEmailFromAuthorizationHeader(String authHeader) {
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String token = authHeader.substring(7).trim();
        String[] parts = token.split("\\.", -1);
        if (parts.length != 3) {
            return null;
        }

        try {
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            return normalizeEmail(extractJsonValue(payload, "email"));
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private String extractJsonValue(String json, String key) {
        String quotedKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(quotedKey);
        if (keyIndex < 0) {
            return null;
        }

        int colonIndex = json.indexOf(':', keyIndex + quotedKey.length());
        if (colonIndex < 0) {
            return null;
        }

        int startIndex = colonIndex + 1;
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }

        if (startIndex >= json.length() || json.charAt(startIndex) != '"') {
            return null;
        }

        int endIndex = json.indexOf('"', startIndex + 1);
        if (endIndex < 0) {
            return null;
        }

        return json.substring(startIndex + 1, endIndex);
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isBlank() ? null : normalized;
    }
}
