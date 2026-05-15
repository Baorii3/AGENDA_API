package com.agenda.itic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.itic.model.Permis;

@Repository
public interface PermisRepository extends JpaRepository<Permis, Long> {

    List<Permis> findAll();

    java.util.Optional<Permis> findByRolIdAndRecursoId(Long rolId, Long recursoId);

    List<Permis> findByRolNombreIgnoreCase(String nombreRol);

    List<Permis> findByRecursoId(Long recursoId);
}