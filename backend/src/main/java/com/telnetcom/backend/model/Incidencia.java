package com.telnetcom.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    private String prioridad;

    private String estado;

    private String usuarioReporta;

    private String tecnicoAsignado;

    @Column(length = 1000)
    private String observacionTecnico;

    private LocalDateTime fechaCreacion;

    public Incidencia() {
    }

    public Incidencia(
            Long id,
            String titulo,
            String descripcion,
            String prioridad,
            String estado,
            String usuarioReporta,
            String tecnicoAsignado,
            String observacionTecnico,
            LocalDateTime fechaCreacion) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.usuarioReporta = usuarioReporta;
        this.tecnicoAsignado = tecnicoAsignado;
        this.observacionTecnico = observacionTecnico;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(String usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public String getObservacionTecnico() {
        return observacionTecnico;
    }

    public void setObservacionTecnico(String observacionTecnico) {
        this.observacionTecnico = observacionTecnico;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}