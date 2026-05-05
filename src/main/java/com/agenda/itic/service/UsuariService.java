package com.agenda.itic.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    WhitelistAdminService whitelistAdminService;

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
                usuari.getFotoPerfil()
        );
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
            if (whitelistAdminService.getWhitelistAdmin(email) != null) {
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
        boolean isNew = user.getId() == null;
        
        user.setEmail(email);
        String name = jwt.getClaim("name");
        user.setNom(name == null ? "Desconocido" : name);
        String picture = jwt.getClaim("picture");
        user.setFotoPerfil(picture);
        
        // Asignar rol solo si es nuevo o no tiene rol (permite overrides manuales en BBDD)
        if (isNew || user.getRol() == null) {
            user.setRol(getRol(email));
        }
        
        user.setActiu(true);
        user.setProvider("google");
        String providerId = jwt.getClaim("sub");
        user.setProviderId(providerId);
        user = usuariRepository.save(user);
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

    private String getTokenEmail(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        return extractClaim(token, "email");
    }

    private String getTokenName(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        return extractClaim(token, "name");
    }

    private String getTokenPicture(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        return extractClaim(token, "picture");
    }

    private String getTokenProviderId(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        String payload = decodeTokenPayload(token);
        if (payload == null) {
            return null;
        }

        String userId = extractJsonValue(payload, "userId");
        if (userId != null) {
            return userId;
        }

        String cognitoUsername = extractJsonValue(payload, "cognito:username");
        if (cognitoUsername != null) {
            return cognitoUsername;
        }

        String sub = extractJsonValue(payload, "sub");
        if (sub != null) {
            return sub;
        }

        return null;
    }

    private String getTokenProvider(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        String payload = decodeTokenPayload(token);
        if (payload == null) {
            return null;
        }

        String providerName = extractJsonValue(payload, "providerName");
        if (providerName != null) {
            return providerName.toLowerCase(Locale.ROOT);
        }

        return null;
    }

    private String extractClaim(String token, String claimName) {
        String payload = decodeTokenPayload(token);
        if (payload == null) {
            return null;
        }
        return extractJsonValue(payload, claimName);
    }

    private String decodeTokenPayload(String token) {
        try {
            String normalizedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String[] parts = normalizedToken.split("\\.", -1);
            if (parts.length != 3) {
                return null;
            }

            byte[] decoded = Base64.getUrlDecoder().decode(parts[1].trim());
            return new String(decoded, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
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

        if (startIndex >= json.length()) {
            return null;
        }

        if (json.charAt(startIndex) == '"') {
            int endIndex = json.indexOf('"', startIndex + 1);
            if (endIndex < 0) {
                return null;
            }
            return json.substring(startIndex + 1, endIndex);
        }

        int endIndex = startIndex;
        while (endIndex < json.length()) {
            char current = json.charAt(endIndex);
            if (current == ',' || current == '}' || current == ']') {
                break;
            }
            endIndex++;
        }

        String value = json.substring(startIndex, endIndex).trim();
        return value.isBlank() ? null : value;
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isBlank() ? null : normalized;
    }

}
