package com.telnetcom.backend.service;

import com.telnetcom.backend.dto.ReporteIncidenciasResponse;
import com.telnetcom.backend.model.Incidencia;
import com.telnetcom.backend.repository.IncidenciaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    private static final String PENDIENTE = "PENDIENTE";
    private static final String ASIGNADA = "ASIGNADA";
    private static final String RESUELTA = "RESUELTA";
    private static final String EN_PROGRESO = "EN_PROGRESO";
    private static final String RESUELTA_POR_TECNICO = "RESUELTA_POR_TECNICO";
    private static final String CONFIRMADA = "CONFIRMADA";
    private static final String REABIERTA = "REABIERTA";
    private static final String BAJA = "BAJA";
    private static final String MEDIA = "MEDIA";
    private static final String ALTA = "ALTA";
    private static final String CRITICA = "CRITICA";

    private final IncidenciaRepository incidenciaRepository;

    public ReporteService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    public ReporteIncidenciasResponse generarReporte(
            String estado,
            String prioridad,
            String tecnico,
            String usuario,
            LocalDate fechaInicio,
            LocalDate fechaFin
    ) {
        List<Incidencia> incidencias = incidenciaRepository.findAll(
                filtros(estado, prioridad, tecnico, usuario, fechaInicio, fechaFin)
        );

        return new ReporteIncidenciasResponse(
                incidencias.size(),
                contarEstado(incidencias, PENDIENTE),
                contarEstado(incidencias, ASIGNADA)
                        + contarEstado(incidencias, EN_PROGRESO)
                        + contarEstado(incidencias, REABIERTA),
                contarEstado(incidencias, RESUELTA)
                        + contarEstado(incidencias, RESUELTA_POR_TECNICO)
                        + contarEstado(incidencias, CONFIRMADA),
                contarPrioridad(incidencias, BAJA),
                contarPrioridad(incidencias, MEDIA),
                contarPrioridad(incidencias, ALTA),
                contarPrioridad(incidencias, CRITICA),
                incidencias
        );
    }

    public String generarCsv(
            String estado,
            String prioridad,
            String tecnico,
            String usuario,
            LocalDate fechaInicio,
            LocalDate fechaFin
    ) {
        ReporteIncidenciasResponse reporte = generarReporte(
                estado,
                prioridad,
                tecnico,
                usuario,
                fechaInicio,
                fechaFin
        );

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Titulo,Descripcion,Prioridad,Estado,Usuario Reporta,Tecnico Asignado,Fecha Creacion,Observacion Tecnico\n");

        for (Incidencia incidencia : reporte.getIncidencias()) {
            csv.append(valor(incidencia.getId())).append(",");
            csv.append(valor(incidencia.getTitulo())).append(",");
            csv.append(valor(incidencia.getDescripcion())).append(",");
            csv.append(valor(incidencia.getPrioridad())).append(",");
            csv.append(valor(incidencia.getEstado())).append(",");
            csv.append(valor(incidencia.getUsuarioReporta())).append(",");
            csv.append(valor(incidencia.getTecnicoAsignado())).append(",");
            csv.append(valor(incidencia.getFechaCreacion())).append(",");
            csv.append(valor(incidencia.getObservacionTecnico())).append("\n");
        }

        return csv.toString();
    }

    private Specification<Incidencia> filtros(
            String estado,
            String prioridad,
            String tecnico,
            String usuario,
            LocalDate fechaInicio,
            LocalDate fechaFin
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (estado != null && !estado.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("estado"), estado));
            }

            if (prioridad != null && !prioridad.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("prioridad"), prioridad));
            }

            if (tecnico != null && !tecnico.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("tecnicoAsignado"), tecnico));
            }

            if (usuario != null && !usuario.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("usuarioReporta"), usuario));
            }

            if (fechaInicio != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("fechaCreacion"),
                        fechaInicio.atStartOfDay()
                ));
            }

            if (fechaFin != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("fechaCreacion"),
                        fechaFin.atTime(23, 59, 59)
                ));
            }

            query.orderBy(criteriaBuilder.desc(root.get("fechaCreacion")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private long contarEstado(List<Incidencia> incidencias, String estado) {
        return incidencias.stream()
                .filter(incidencia -> estado.equals(incidencia.getEstado()))
                .count();
    }

    private long contarPrioridad(List<Incidencia> incidencias, String prioridad) {
        return incidencias.stream()
                .filter(incidencia -> prioridad.equals(incidencia.getPrioridad()))
                .count();
    }

    private String valor(Object valor) {
        if (valor == null) {
            return "";
        }

        String texto = valor.toString().replace("\"", "\"\"");
        return "\"" + texto + "\"";
    }
}
