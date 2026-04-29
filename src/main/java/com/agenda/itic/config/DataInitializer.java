package com.agenda.itic.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.agenda.itic.model.Permis;
import com.agenda.itic.model.CorreoPermitido;
import com.agenda.itic.model.Recurs;
import com.agenda.itic.model.RecursNom;
import com.agenda.itic.model.Rol;
import com.agenda.itic.model.Sala;
import com.agenda.itic.model.Usuari;
import com.agenda.itic.repository.CorreoPermitidoRepository;
import com.agenda.itic.repository.PermisRepository;
import com.agenda.itic.repository.RecursRepository;
import com.agenda.itic.repository.RolRepository;
import com.agenda.itic.repository.SalaRepository;
import com.agenda.itic.repository.UsuariRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final RecursRepository recursRepository;
    private final PermisRepository permisRepository;
    private final SalaRepository salaRepository;
    private final UsuariRepository usuariRepository;
    private final CorreoPermitidoRepository correoPermitidoRepository;

    public DataInitializer(
            RolRepository rolRepository,
            RecursRepository recursRepository,
            PermisRepository permisRepository,
            SalaRepository salaRepository,
            UsuariRepository usuariRepository,
            CorreoPermitidoRepository correoPermitidoRepository) {
        this.rolRepository = rolRepository;
        this.recursRepository = recursRepository;
        this.permisRepository = permisRepository;
        this.salaRepository = salaRepository;
        this.usuariRepository = usuariRepository;
        this.correoPermitidoRepository = correoPermitidoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Rol admin = ensureRol("ADMIN");
        Rol professor = ensureRol("PROFESSOR");
        Rol usuari = ensureRol("USUARI");

        Recurs salas = ensureRecurs(RecursNom.SALA);
        Recurs activitats = ensureRecurs(RecursNom.ACTIVITAT);
        Recurs usuaris = ensureRecurs(RecursNom.USUARI);

        ensurePermis(admin, salas, 15);
        ensurePermis(admin, activitats, 15);
        ensurePermis(admin, usuaris, 15);

        ensurePermis(professor, salas, 7);
        ensurePermis(professor, activitats, 7);
        ensurePermis(professor, usuaris, 1);

        ensurePermis(usuari, salas, 1);
        ensurePermis(usuari, activitats, 1);

        ensureSala("Aula 101", Sala.Color.AZUL, "Planta baixa");
        ensureSala("Aula 202", Sala.Color.VERDE, "Planta 4");
        ensureSala("Sala de Juntes", Sala.Color.MORADO, "Sala principal");

        ensureUsuari("Admin Demo", "admin@iticbcn.cat", admin, "local", "admin-demo", "https://placehold.co/128x128");
        ensureUsuari("Professor Demo", "professor@iticbcn.cat", professor, "local", "prof-demo", "https://placehold.co/128x128");
        ensureUsuari("Usuari Demo", "usuari_1@iticbcn.cat", usuari, "local", "user-demo", "https://placehold.co/128x128");

        ensureCorreoPermitido("2223_ian.ordonez@iticbcn.cat");
        ensureCorreoPermitido("2024_juli.farres@iticbcn.cat");
    }

    private Rol ensureRol(String nombre) {
        return rolRepository.findByNombreIgnoreCase(nombre)
                .orElseGet(() -> rolRepository.save(new Rol(nombre)));
    }

    private Recurs ensureRecurs(RecursNom nombre) {
        return recursRepository.findAll().stream()
                .filter(recurs -> recurs.getNombre() == nombre)
                .findFirst()
                .orElseGet(() -> recursRepository.save(new Recurs(nombre)));
    }

    private Permis ensurePermis(Rol rol, Recurs recurs, int valueAccio) {
        return permisRepository.findByRolIdAndRecursoId(rol.getId(), recurs.getId())
                .map(existing -> {
                    if (existing.getValueAccio() != valueAccio) {
                        existing.setAcciones(valueAccio);
                        return permisRepository.save(existing);
                    }
                    return existing;
                })
                .orElseGet(() -> {
                    Permis permis = new Permis();
                    permis.setRol(rol);
                    permis.setRecurso(recurs);
                    permis.setAcciones(valueAccio);
                    return permisRepository.save(permis);
                });
    }

    private Sala ensureSala(String nombre, Sala.Color color, String descripcio) {
        return salaRepository.findAll().stream()
                .filter(sala -> nombre.equalsIgnoreCase(sala.getNom()))
                .findFirst()
                .orElseGet(() -> {
                    Sala sala = new Sala();
                    sala.setNom(nombre);
                    sala.setUbicacio(com.agenda.itic.model.PisoSala.P0);
                    sala.setDescripcio(descripcio);
                    sala.setActiva(true);
                    sala.setColor(color);
                    sala.setDataCreacio(LocalDateTime.now());
                    sala.setDataModificacio(LocalDateTime.now());
                    return salaRepository.save(sala);
                });
    }

    private Usuari ensureUsuari(String nom, String email, Rol rol, String provider, String providerId, String fotoPerfil) {
        return usuariRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuari usuari = new Usuari();
                    usuari.setNom(nom);
                    usuari.setEmail(email);
                    usuari.setRol(rol);
                    usuari.setActiu(true);
                    usuari.setProvider(provider);
                    usuari.setProviderId(providerId);
                    usuari.setFotoPerfil(fotoPerfil);
                    return usuariRepository.save(usuari);
                });
    }

    private CorreoPermitido ensureCorreoPermitido(String correo) {
        CorreoPermitido existing = correoPermitidoRepository.findByCorreoIgnoreCase(correo);
        if (existing != null) {
            return existing;
        }
        return correoPermitidoRepository.save(new CorreoPermitido(correo));
    }
}