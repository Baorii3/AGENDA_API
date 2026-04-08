package com.agenda.itic.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.agenda.itic.dto.UsuariRequestDTO;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.model.Usuari.Rol;
import com.agenda.itic.repository.UsuariRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuariService {

    @Autowired
    UsuariRepository usuariRepository;

    @Autowired
    CorreoPermitidoService correoPermitidoService;

    public List<Usuari> getUsuaris() {
        return usuariRepository.findAll();
    }

    public List<Usuari> getUsuarisActius(Boolean actiu) {
        return usuariRepository.findByActiu(actiu);
    }

    public Usuari createUsuari(UsuariRequestDTO usuariRequestDTO) {
        if (correoPermitidoService.getCorreoPermitido(usuariRequestDTO.getEmail()) == null) {
            return null;
        }
        try {
            Usuari usuari = new Usuari();
            usuari.setNom(usuariRequestDTO.getNom());
            usuari.setEmail(usuariRequestDTO.getEmail());
            usuari.setRol(usuariRequestDTO.getRol() != null ? usuariRequestDTO.getRol() : Rol.usuari);
            usuari.setActiu(usuariRequestDTO.getActiu() != null ? usuariRequestDTO.getActiu() : false);
            usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : "local");
            usuari.setProviderId(usuariRequestDTO.getProviderId());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
        }
    }

    public Usuari createOrUpdateOAuthUsuari(UsuariRequestDTO usuariRequestDTO) {
        if (usuariRequestDTO == null || usuariRequestDTO.getEmail() == null) {
            return null;
        }
        if (correoPermitidoService.getCorreoPermitido(usuariRequestDTO.getEmail()) == null) {
            return null;
        }
        try {
            Usuari usuari = usuariRepository.findByEmail(usuariRequestDTO.getEmail()).orElseGet(Usuari::new);
            if (usuari.getId() == null) {
                usuari.setNom(usuariRequestDTO.getNom());
                usuari.setEmail(usuariRequestDTO.getEmail());
                usuari.setRol(Rol.usuari);
                usuari.setActiu(true);
                usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : "local");
                usuari.setProviderId(usuariRequestDTO.getProviderId());
            }
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
        }
    }

    public Usuari updateUsuari(Long id, UsuariRequestDTO usuariRequestDTO) {
        if (usuariRequestDTO == null) {
            return null;
        }
        try {
            Usuari usuari = usuariRepository.findById(id).get();
            usuari.setNom(usuariRequestDTO.getNom());
            usuari.setEmail(usuariRequestDTO.getEmail());
            usuari.setRol(usuariRequestDTO.getRol() != null ? usuariRequestDTO.getRol() : Rol.usuari);
            usuari.setActiu(usuariRequestDTO.getActiu() != null ? usuariRequestDTO.getActiu() : false);
            usuari.setProvider(usuariRequestDTO.getProvider() != null ? usuariRequestDTO.getProvider() : usuari.getProvider());
            usuari.setProviderId(usuariRequestDTO.getProviderId() != null ? usuariRequestDTO.getProviderId() : usuari.getProviderId());
            return usuariRepository.save(usuari);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteUsuari(Long id) {
        if (!usuariRepository.existsById(id)) {
            return false;
        }
        
        try {
            usuariRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }


    // Crearemos un usuario o no, a traves de un token
    public Usuari createOrUpdateUsuariFromToken(String token) {
        String email = normalizeEmail(getTokenEmail(token));

        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido o sin email");
        }

        Optional<Usuari> usuariOptional = usuariRepository.findByEmail(email);
        if (usuariOptional.isPresent()) {
            return usuariOptional.get();
        } else {
            if (correoPermitidoService.getCorreoPermitido(email) == null) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Correo no permitido en la lista blanca");            
            }
        }
        
        Usuari user = new Usuari();
        user.setEmail(email);
        user.setNom(getTokenName(token) == null ? "Desconocido" : getTokenName(token));
        user.setRol(Rol.usuari);
        user.setActiu(true);
        user.setProvider(getTokenProvider(token) == null ? "cognito" : getTokenProvider(token));
        user.setProviderId(getTokenProviderId(token));
        try {
            user = usuariRepository.save(user);
        } catch (Exception e) {
            throw e;

        }
        return user;
    }

    private String getTokenEmail(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        try {
            String normalizedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String[] parts = normalizedToken.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            String payload = new String(decoded, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });
            Object email = claims.get("email");

            return email != null ? email.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getTokenName(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        try {
            String normalizedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String[] parts = normalizedToken.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            String payload = new String(decoded, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });
            Object name = claims.get("name");

            return name != null ? name.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getTokenProviderId(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        try {
            String normalizedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String[] parts = normalizedToken.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            String payload = new String(decoded, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });

            Object identities = claims.get("identities");
            if (identities instanceof List<?> identitiesList && !identitiesList.isEmpty()) {
                Object firstIdentity = identitiesList.get(0);
                if (firstIdentity instanceof Map<?, ?> identityMap) {
                    Object userId = identityMap.get("userId");
                    if (userId != null) {
                        return userId.toString();
                    }
                }
            }

            Object cognitoUsername = claims.get("cognito:username");
            if (cognitoUsername != null) {
                return cognitoUsername.toString();
            }

            Object sub = claims.get("sub");
            if (sub != null) {
                return sub.toString();
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getTokenProvider(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        try {
            String normalizedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String[] parts = normalizedToken.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            String payload = new String(decoded, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });

            Object identities = claims.get("identities");
            if (identities instanceof List<?> identitiesList && !identitiesList.isEmpty()) {
                Object firstIdentity = identitiesList.get(0);
                if (firstIdentity instanceof Map<?, ?> identityMap) {
                    Object providerName = identityMap.get("providerName");
                    if (providerName != null) {
                        return providerName.toString().toLowerCase(Locale.ROOT);
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim().toLowerCase(Locale.ROOT);
        return normalized.isBlank() ? null : normalized;
    }

}
