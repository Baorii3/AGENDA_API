package com.agenda.itic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.itic.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findAll();

    Optional<Rol> findByNombreIgnoreCase(String nombre);
}