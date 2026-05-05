package com.agenda.itic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.itic.model.WhitelistAdmin;

@Repository
public interface WhitelistAdminRepository extends JpaRepository<WhitelistAdmin, Long> {
    WhitelistAdmin findByCorreoIgnoreCase(String correo);
}
