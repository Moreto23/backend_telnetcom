package com.telnetcom.backend.service;

import com.telnetcom.backend.dto.CrearIncidenciaRequest;
import com.telnetcom.backend.dto.SeguimientoIncidenciaResponse;
import com.telnetcom.backend.model.Incidencia;
import com.telnetcom.backend.model.SeguimientoIncidencia;
import com.telnetcom.backend.repository.IncidenciaRepository;
import com.telnetcom.backend.repository.SeguimientoIncidenciaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaService {

    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_ASIGNADA = "ASIGNADA";
    private static final String ESTADO_RESUELTA = "RESUELTA";
    private static final String ESTADO_EN_PROGRESO = "EN_PROGRESO";
    private static final String ESTADO_RESUELTA_POR_TECNICO = "RESUELTA_POR_TECNICO";
    private static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    private static final String ESTADO_REABIERTA = "REABIERTA";

    private final IncidenciaRepository incidenciaRepository;
    private final SeguimientoIncidenciaRepository seguimientoRepository;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            SeguimientoIncidenciaRepository seguimientoRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.seguimientoRepository = seguimientoRepository;
    }

    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    public Incidencia crear(CrearIncidenciaRequest request) {
        Incidencia incidencia = new Incidencia();

        incidencia.setTitulo(request.getTitulo().trim());
        incidencia.setDescripcion(request.getDescripcion().trim());
        incidencia.setPrioridad(request.getPrioridad());
        incidencia.setUsuarioReporta(request.getUsuarioReporta().trim());
        incidencia.setEstado(ESTADO_PENDIENTE);
        incidencia.setFechaCreacion(LocalDateTime.now());

        Incidencia guardada = incidenciaRepository.save(incidencia);
        registrarSeguimiento(
                guardada,
                ESTADO_PENDIENTE,
                "CREACION",
                "Incidencia registrada y pendiente de asignacion.",
                null,
                guardada.getUsuarioReporta()
        );

        return guardada;
    }

    public Incidencia obtenerPorId(Long id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Incidencia no encontrada"
                ));
    }

    public Incidencia asignarTecnico(Long id, String tecnico) {
        if (tecnico == null || tecnico.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tecnico es obligatorio"
            );
        }

        Incidencia incidencia = obtenerPorId(id);
        incidencia.setTecnicoAsignado(tecnico.trim());
        incidencia.setEstado(ESTADO_ASIGNADA);

        Incidencia guardada = incidenciaRepository.save(incidencia);
        registrarSeguimiento(
                guardada,
                ESTADO_ASIGNADA,
                "ASIGNACION",
                "Incidencia asignada al tecnico " + tecnico.trim() + ".",
                null,
                tecnico.trim()
        );

        return guardada;
    }

    public Incidencia cambiarEstado(Long id, String estado, String observacion) {
        return cambiarEstado(id, estado, observacion, "ACTUALIZACION_TECNICA", null);
    }

    public Incidencia cambiarEstado(
            Long id,
            String estado,
            String observacion,
            String tipo,
            String adjuntoUrl
    ) {
        validarEstado(estado);

        Incidencia incidencia = obtenerPorId(id);
        String estadoNormalizado = normalizarEstado(estado);
        incidencia.setEstado(estadoNormalizado);

        String observacionNormalizada = observacion == null
                ? ""
                : observacion.trim();

        if (!observacionNormalizada.isBlank()) {
            incidencia.setObservacionTecnico(observacionNormalizada);
        }

        Incidencia guardada = incidenciaRepository.save(incidencia);
        registrarSeguimiento(
                guardada,
                estadoNormalizado,
                tipo == null || tipo.isBlank()
                        ? "ACTUALIZACION_TECNICA"
                        : tipo.trim(),
                observacionNormalizada.isBlank()
                        ? "Estado actualizado a " + estadoNormalizado + "."
                        : observacionNormalizada,
                adjuntoUrl,
                guardada.getTecnicoAsignado()
        );

        return guardada;
    }

    public Incidencia confirmarSolucion(Long id, String usuario, String observacion) {
        Incidencia incidencia = obtenerPorId(id);

        if (!ESTADO_RESUELTA_POR_TECNICO.equals(incidencia.getEstado())
                && !ESTADO_RESUELTA.equals(incidencia.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se puede confirmar una incidencia resuelta por tecnico"
            );
        }

        incidencia.setEstado(ESTADO_CONFIRMADA);
        Incidencia guardada = incidenciaRepository.save(incidencia);

        registrarSeguimiento(
                guardada,
                ESTADO_CONFIRMADA,
                "CONFIRMACION_USUARIO",
                observacion == null || observacion.isBlank()
                        ? "El usuario confirmo que la solucion fue satisfactoria."
                        : observacion.trim(),
                null,
                usuario
        );

        return guardada;
    }

    public Incidencia reabrirIncidencia(Long id, String usuario, String observacion) {
        Incidencia incidencia = obtenerPorId(id);

        incidencia.setEstado(ESTADO_REABIERTA);
        Incidencia guardada = incidenciaRepository.save(incidencia);

        registrarSeguimiento(
                guardada,
                ESTADO_REABIERTA,
                "REAPERTURA_USUARIO",
                observacion == null || observacion.isBlank()
                        ? "El usuario reabrio la incidencia porque el problema persiste."
                        : observacion.trim(),
                null,
                usuario
        );

        return guardada;
    }

    public SeguimientoIncidenciaResponse agregarSeguimiento(
            Long id,
            String tipo,
            String observacion,
            String registradoPor,
            String adjuntoUrl
    ) {
        Incidencia incidencia = obtenerPorId(id);

        if (observacion == null || observacion.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La observacion es obligatoria"
            );
        }

        SeguimientoIncidencia seguimiento = registrarSeguimiento(
                incidencia,
                incidencia.getEstado(),
                tipo == null || tipo.isBlank() ? "OBSERVACION" : tipo.trim(),
                observacion.trim(),
                adjuntoUrl,
                registradoPor
        );

        return mapearSeguimiento(seguimiento);
    }

    public List<SeguimientoIncidenciaResponse> listarSeguimientos(Long incidenciaId) {
        obtenerPorId(incidenciaId);

        return seguimientoRepository
                .findByIncidenciaIdOrderByFechaRegistroAsc(incidenciaId)
                .stream()
                .map(this::mapearSeguimiento)
                .toList();
    }

    public void eliminar(Long id) {
        if (!incidenciaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Incidencia no encontrada"
            );
        }

        incidenciaRepository.deleteById(id);
    }

    public List<Incidencia> listarPorUsuario(String username) {
        return incidenciaRepository.findByUsuarioReporta(username);
    }

    public List<Incidencia> listarPorTecnico(String tecnico) {
        return incidenciaRepository.findByTecnicoAsignado(tecnico);
    }

    public List<Incidencia> listarPorEstado(String estado) {
        validarEstado(estado);
        return incidenciaRepository.findByEstado(estado);
    }

    public List<Incidencia> listarPorTecnicoYEstado(String tecnico, String estado) {
        validarEstado(estado);
        return incidenciaRepository.findByTecnicoAsignadoAndEstado(tecnico, estado);
    }

    private void validarEstado(String estado) {
        if (!ESTADO_PENDIENTE.equals(estado)
                && !ESTADO_ASIGNADA.equals(estado)
                && !ESTADO_EN_PROGRESO.equals(estado)
                && !ESTADO_RESUELTA.equals(estado)
                && !ESTADO_RESUELTA_POR_TECNICO.equals(estado)
                && !ESTADO_CONFIRMADA.equals(estado)
                && !ESTADO_REABIERTA.equals(estado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Estado invalido"
            );
        }
    }

    private String normalizarEstado(String estado) {
        if (ESTADO_RESUELTA.equals(estado)) {
            return ESTADO_RESUELTA_POR_TECNICO;
        }

        return estado;
    }

    private SeguimientoIncidencia registrarSeguimiento(
            Incidencia incidencia,
            String estado,
            String tipo,
            String observacion,
            String adjuntoUrl,
            String registradoPor
    ) {
        SeguimientoIncidencia seguimiento = new SeguimientoIncidencia();
        seguimiento.setIncidencia(incidencia);
        seguimiento.setEstado(estado);
        seguimiento.setTipo(tipo);
        seguimiento.setObservacion(observacion);
        seguimiento.setAdjuntoUrl(adjuntoUrl == null || adjuntoUrl.isBlank()
                ? null
                : adjuntoUrl.trim());
        seguimiento.setRegistradoPor(
                registradoPor == null || registradoPor.isBlank()
                        ? "Sistema"
                        : registradoPor
        );
        seguimiento.setFechaRegistro(LocalDateTime.now());

        return seguimientoRepository.save(seguimiento);
    }

    private SeguimientoIncidenciaResponse mapearSeguimiento(
            SeguimientoIncidencia seguimiento
    ) {
        return new SeguimientoIncidenciaResponse(
                seguimiento.getId(),
                seguimiento.getIncidencia().getId(),
                seguimiento.getEstado(),
                seguimiento.getTipo(),
                seguimiento.getObservacion(),
                seguimiento.getAdjuntoUrl(),
                seguimiento.getRegistradoPor(),
                seguimiento.getFechaRegistro()
        );
    }
}
