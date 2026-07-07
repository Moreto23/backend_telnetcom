package com.telnetcom.backend.controller;

import com.telnetcom.backend.dto.CrearIncidenciaRequest;
import com.telnetcom.backend.dto.SeguimientoIncidenciaResponse;
import com.telnetcom.backend.model.Incidencia;
import com.telnetcom.backend.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/incidencias")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public List<Incidencia> listar() {
        return incidenciaService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Incidencia> crear(
            @Valid @RequestBody CrearIncidenciaRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(incidenciaService.crear(request));
    }

    @GetMapping("/{id}")
    public Incidencia obtenerPorId(@PathVariable Long id) {
        return incidenciaService.obtenerPorId(id);
    }

    @GetMapping("/{id}/seguimientos")
    public List<SeguimientoIncidenciaResponse> listarSeguimientos(
            @PathVariable Long id
    ) {
        return incidenciaService.listarSeguimientos(id);
    }

    @PutMapping("/{id}/asignar")
    public Incidencia asignarTecnico(
            @PathVariable Long id,
            @RequestParam String tecnico
    ) {
        return incidenciaService.asignarTecnico(id, tecnico);
    }

    @PutMapping("/{id}/estado")
    public Incidencia cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado,
            @RequestParam(required = false) String observacion,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String adjuntoUrl
    ) {
        return incidenciaService.cambiarEstado(id, estado, observacion, tipo, adjuntoUrl);
    }

    @PutMapping("/{id}/confirmar")
    public Incidencia confirmarSolucion(
            @PathVariable Long id,
            @RequestParam String usuario,
            @RequestParam(required = false) String observacion
    ) {
        return incidenciaService.confirmarSolucion(id, usuario, observacion);
    }

    @PutMapping("/{id}/reabrir")
    public Incidencia reabrirIncidencia(
            @PathVariable Long id,
            @RequestParam String usuario,
            @RequestParam(required = false) String observacion
    ) {
        return incidenciaService.reabrirIncidencia(id, usuario, observacion);
    }

    @PostMapping("/{id}/seguimientos")
    public SeguimientoIncidenciaResponse agregarSeguimiento(
            @PathVariable Long id,
            @RequestParam(required = false) String tipo,
            @RequestParam String observacion,
            @RequestParam(required = false) String registradoPor,
            @RequestParam(required = false) String adjuntoUrl
    ) {
        return incidenciaService.agregarSeguimiento(
                id,
                tipo,
                observacion,
                registradoPor,
                adjuntoUrl
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        incidenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{username}")
    public List<Incidencia> listarPorUsuario(@PathVariable String username) {
        return incidenciaService.listarPorUsuario(username);
    }

    @GetMapping("/tecnico/{username}")
    public List<Incidencia> listarPorTecnico(@PathVariable String username) {
        return incidenciaService.listarPorTecnico(username);
    }

    @GetMapping("/estado/{estado}")
    public List<Incidencia> listarPorEstado(@PathVariable String estado) {
        return incidenciaService.listarPorEstado(estado);
    }

    @GetMapping("/tecnico/{username}/estado/{estado}")
    public List<Incidencia> listarPorTecnicoYEstado(
            @PathVariable String username,
            @PathVariable String estado
    ) {
        return incidenciaService.listarPorTecnicoYEstado(username, estado);
    }
}
