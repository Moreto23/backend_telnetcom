package com.telnetcom.backend.dto;

import java.time.LocalDateTime;

public class SeguimientoIncidenciaResponse {

    private Long id;
    private Long incidenciaId;
    private String estado;
    private String tipo;
    private String observacion;
    private String adjuntoUrl;
    private String registradoPor;
    private LocalDateTime fechaRegistro;

    public SeguimientoIncidenciaResponse(
            Long id,
            Long incidenciaId,
            String estado,
            String tipo,
            String observacion,
            String adjuntoUrl,
            String registradoPor,
            LocalDateTime fechaRegistro
    ) {
        this.id = id;
        this.incidenciaId = incidenciaId;
        this.estado = estado;
        this.tipo = tipo;
        this.observacion = observacion;
        this.adjuntoUrl = adjuntoUrl;
        this.registradoPor = registradoPor;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public Long getIncidenciaId() {
        return incidenciaId;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getAdjuntoUrl() {
        return adjuntoUrl;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
