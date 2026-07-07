package com.telnetcom.backend.dto;

public class LoginResponse {

    private String username;
    private String rol;
    private String mensaje;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(
            String username,
            String rol,
            String mensaje,
            String token
    ) {
        this.username = username;
        this.rol = rol;
        this.mensaje = mensaje;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}