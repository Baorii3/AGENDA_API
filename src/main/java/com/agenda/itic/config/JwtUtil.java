package com.agenda.itic.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtUtil {
    
    @Value("${aws.cognito.region}")
    private String region;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    // Validar token de Cognito
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado: " + e.getMessage());
            return false;
        } catch (SignatureException e) {
            System.out.println("Firma inválida: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error validando token: " + e.getMessage());
            return false;
        }
    }

    // Obtener claims del token (sin validar firma por ahora - requiere JWKS)
    public Claims getClaims(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new MalformedJwtException("Token JWT inválido");
        }
        
        String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
        
        return Jwts.parser()
                .unsecured()
                .build()
                .parseUnsecuredClaims(token)
                .getPayload();
    }

    // Extraer información específica del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtener email del token (cognito:username o email)
    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        String email = claims.get("email", String.class);
        if (email == null) {
            email = claims.get("cognito:username", String.class);
        }
        if (email == null) {
            email = claims.getSubject();
        }
        return email;
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration)
                    .before(new java.util.Date());
        } catch (Exception e) {
            return true;
        }
    }


}
