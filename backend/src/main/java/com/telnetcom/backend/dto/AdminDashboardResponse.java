package com.telnetcom.backend.dto;

public class AdminDashboardResponse {

    private long totalIncidencias;
    private long pendientes;
    private long asignadas;
    private long resueltas;
    private long sinAsignar;
    private long totalUsuarios;
    private long totalTecnicos;
    private long prioridadBaja;
    private long prioridadMedia;
    private long prioridadAlta;
    private long prioridadCritica;

    public AdminDashboardResponse(
            long totalIncidencias,
            long pendientes,
            long asignadas,
            long resueltas,
            long sinAsignar,
            long totalUsuarios,
            long totalTecnicos,
            long prioridadBaja,
            long prioridadMedia,
            long prioridadAlta,
            long prioridadCritica
    ) {
        this.totalIncidencias = totalIncidencias;
        this.pendientes = pendientes;
        this.asignadas = asignadas;
        this.resueltas = resueltas;
        this.sinAsignar = sinAsignar;
        this.totalUsuarios = totalUsuarios;
        this.totalTecnicos = totalTecnicos;
        this.prioridadBaja = prioridadBaja;
        this.prioridadMedia = prioridadMedia;
        this.prioridadAlta = prioridadAlta;
        this.prioridadCritica = prioridadCritica;
    }

    public long getTotalIncidencias() {
        return totalIncidencias;
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

    public long getSinAsignar() {
        return sinAsignar;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public long getTotalTecnicos() {
        return totalTecnicos;
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
}
