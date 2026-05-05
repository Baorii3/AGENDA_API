package com.agenda.itic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.itic.dto.WhitelistAdminRequestDto;
import com.agenda.itic.dto.WhitelistAdminResponseDto;
import com.agenda.itic.service.WhitelistAdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/whitelist-admins")
public class WhitelistAdminController {

    @Autowired
    WhitelistAdminService whitelistAdminService;

    @GetMapping
    public ResponseEntity<List<WhitelistAdminResponseDto>> getAll() {
        return ResponseEntity.ok(whitelistAdminService.getAllWhitelistAdmins());
    }

    @PostMapping
    public ResponseEntity<WhitelistAdminResponseDto> create(@Valid @RequestBody WhitelistAdminRequestDto request) {
        return ResponseEntity.ok(whitelistAdminService.createWhitelistAdmin(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        whitelistAdminService.deleteWhitelistAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
