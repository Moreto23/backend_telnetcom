package com.telnetcom.backend.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ActualizarUsuarioRequest {

    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @Size(min = 4, max = 100, message = "La password debe tener entre 4 y 100 caracteres")
    private String password;

    @Pattern(
            regexp = "ADMIN|TECNICO|USUARIO",
            message = "El rol debe ser ADMIN, TECNICO o USUARIO"
    )
    private String rol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
