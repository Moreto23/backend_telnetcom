package com.telnetcom.backend.dto;

public class UsuarioDashboardResponse {

    private long generadas;
    private long pendientes;
    private long asignadas;
    private long resueltas;

    public UsuarioDashboardResponse(
            long generadas,
            long pendientes,
            long asignadas,
            long resueltas
    ) {
        this.generadas = generadas;
        this.pendientes = pendientes;
        this.asignadas = asignadas;
        this.resueltas = resueltas;
    }

    public long getGeneradas() {
        return generadas;
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
}
