package com.telnetcom.backend.dto;

import com.telnetcom.backend.model.Incidencia;

import java.util.List;

public class ReporteIncidenciasResponse {

    private long total;
    private long pendientes;
    private long asignadas;
    private long resueltas;
    private long prioridadBaja;
    private long prioridadMedia;
    private long prioridadAlta;
    private long prioridadCritica;
    private List<Incidencia> incidencias;

    public ReporteIncidenciasResponse(
            long total,
            long pendientes,
            long asignadas,
            long resueltas,
            long prioridadBaja,
            long prioridadMedia,
            long prioridadAlta,
            long prioridadCritica,
            List<Incidencia> incidencias
    ) {
        this.total = total;
        this.pendientes = pendientes;
        this.asignadas = asignadas;
        this.resueltas = resueltas;
        this.prioridadBaja = prioridadBaja;
        this.prioridadMedia = prioridadMedia;
        this.prioridadAlta = prioridadAlta;
        this.prioridadCritica = prioridadCritica;
        this.incidencias = incidencias;
    }

    public long getTotal() {
        return total;
    }

    public long getPendientes() {
        return pendientes;
    }

    public long getAsignadas() {
        return asignadas;
    }

    public long getResueltas() {
        return resueltas;
    }

    public long getPrioridadBaja() {
        return prioridadBaja;
    }

    public long getPrioridadMedia() {
        return prioridadMedia;
    }

    public long getPrioridadAlta() {
        return prioridadAlta;
    }

    public long getPrioridadCritica() {
        return prioridadCritica;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }
}
