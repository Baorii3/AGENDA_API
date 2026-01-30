package com.agenda.itic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.agenda.itic.model.Usuari;

@Repository
public interface UsuariRepository extends JpaRepository<Usuari, Long> {
    List<Usuari> findByActiu(boolean actiu);
}
