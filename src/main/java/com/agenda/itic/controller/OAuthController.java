package com.agenda.itic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.UsuariRequestDTO;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.service.UsuariService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/oauth/google")
@CrossOrigin(origins = "*")
public class OAuthController {
    
    @Autowired
    UsuariService usuariService;
    
    @GetMapping("/home")
    public ResponseEntity<String> home(Authentication authentication) {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        UsuariRequestDTO dto = new UsuariRequestDTO();
        dto.setEmail(user.getAttribute("email"));
        dto.setNom(user.getAttribute("name"));
        Usuari usuari = usuariService.createUsuari(dto);
        if (usuari == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear l'usuari");
        }
        return ResponseEntity.ok("Usuari creat correctament");
    }   

    
    
}
