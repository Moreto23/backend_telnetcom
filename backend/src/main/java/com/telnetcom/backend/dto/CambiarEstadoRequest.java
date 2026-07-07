package com.telnetcom.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CambiarEstadoRequest {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|ASIGNADA|EN_PROGRESO|RESUELTA|RESUELTA_POR_TECNICO|CONFIRMADA|REABIERTA",
            message = "Estado invalido"
    )
    private String estado;

    @Size(max = 1000, message = "La observacion no debe superar 1000 caracteres")
    private String observacion;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
