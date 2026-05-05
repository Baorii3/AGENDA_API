package com.agenda.itic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record WhitelistAdminRequestDto(
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El formato del correo no es válido")
    String correo
) {
    public String getCorreo() {
        return correo;
    }
}
