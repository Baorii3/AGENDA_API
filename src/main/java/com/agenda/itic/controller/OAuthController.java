package com.agenda.itic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.service.WhitelistAdminService;
import com.agenda.itic.service.UsuariService;


@RestController
@RequestMapping("/oauth/google")
@CrossOrigin(origins = "*")
public class OAuthController {
    
    @Autowired
    UsuariService usuariService;

    @Autowired
    WhitelistAdminService whitelistAdminService;
    
    @Value("${app.frontend.url}")
    private String frontendUrl;

    @GetMapping("/home")
    public ResponseEntity<Void> home() {
        return ResponseEntity.status(302).header("Location", frontendUrl).build();
    }

}
