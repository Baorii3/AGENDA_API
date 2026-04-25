package com.agenda.itic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.itic.model.Recurs;

@Repository
public interface RecursRepository extends JpaRepository<Recurs, Long> {

    List<Recurs> findAll();

    Optional<Recurs> findByNombreIgnoreCase(String nombre);
}