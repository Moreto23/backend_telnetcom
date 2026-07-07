package com.telnetcom.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CrearIncidenciaRequest {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 5, max = 150, message = "El titulo debe tener entre 5 y 150 caracteres")
    private String titulo;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 10, max = 1000, message = "La descripcion debe tener entre 10 y 1000 caracteres")
    private String descripcion;

    @NotBlank(message = "La prioridad es obligatoria")
    @Pattern(
            regexp = "BAJA|MEDIA|ALTA|CRITICA",
            message = "La prioridad debe ser BAJA, MEDIA, ALTA o CRITICA"
    )
    private String prioridad;

    @NotBlank(message = "El usuario que reporta es obligatorio")
    private String usuarioReporta;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(String usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }
}
