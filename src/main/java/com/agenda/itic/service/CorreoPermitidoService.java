package com.agenda.itic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenda.itic.model.CorreoPermitido;
import com.agenda.itic.repository.CorreoPermitidoRepository;

@Service
public class CorreoPermitidoService {

    @Autowired
    CorreoPermitidoRepository correopermitidorepository;

    public List<CorreoPermitido> getAllCorreosPermitidos() {
        return correopermitidorepository.findAll();
    }

    public CorreoPermitido getCorreoPermitido(String email) {
        return correopermitidorepository.findByCorreo(email);
    }


    public CorreoPermitido createCorreoPermitido(CorreoPermitido correoPermitido) {
        if (correopermitidorepository.findByCorreo(correoPermitido.getCorreo()) != null) {
            throw new RuntimeException("Correo ya registrado");
        }
        try {
            return correopermitidorepository.save(correoPermitido);
        } catch (Exception e) {
            throw new RuntimeException("Error creando un correo permitido", e);
        }

    }

}