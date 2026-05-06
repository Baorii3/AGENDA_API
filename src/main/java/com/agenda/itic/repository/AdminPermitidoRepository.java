package com.agenda.itic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.itic.model.AdminPermitido;

@Repository
public interface AdminPermitidoRepository extends JpaRepository<AdminPermitido, Long> {
    AdminPermitido findByCorreoIgnoreCase(String correo);
}
