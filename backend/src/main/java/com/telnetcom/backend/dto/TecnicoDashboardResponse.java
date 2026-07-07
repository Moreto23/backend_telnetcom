package com.telnetcom.backend.dto;

public class TecnicoDashboardResponse {

    private long asignadas;
    private long pendientes;
    private long resueltas;
    private long prioridadAlta;
    private long prioridadCritica;

    public TecnicoDashboardResponse(
            long asignadas,
            long pendientes,
            long resueltas,
            long prioridadAlta,
            long prioridadCritica
    ) {
        this.asignadas = asignadas;
        this.pendientes = pendientes;
        this.resueltas = resueltas;
        this.prioridadAlta = prioridadAlta;
        this.prioridadCritica = prioridadCritica;
    }

    public long getAsignadas() {
        return asignadas;
    }

    public long getPendientes() {
        return pendientes;
    }

    public long getResueltas() {
        return resueltas;
    }

    public long getPrioridadAlta() {
        return prioridadAlta;
    }

    public long getPrioridadCritica() {
        return prioridadCritica;
    }
}
