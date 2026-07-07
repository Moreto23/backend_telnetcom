package com.telnetcom.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AsignarTecnicoRequest {

    @NotBlank(message = "El tecnico es obligatorio")
    private String tecnico;

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
}
