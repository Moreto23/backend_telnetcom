package com.telnetcom.backend.service;

import com.telnetcom.backend.dto.AdminDashboardResponse;
import com.telnetcom.backend.dto.TecnicoDashboardResponse;
import com.telnetcom.backend.dto.UsuarioDashboardResponse;
import com.telnetcom.backend.model.Rol;
import com.telnetcom.backend.repository.IncidenciaRepository;
import com.telnetcom.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

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
    private final UsuarioRepository usuarioRepository;

    public DashboardService(
            IncidenciaRepository incidenciaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public AdminDashboardResponse obtenerAdminDashboard() {
        return new AdminDashboardResponse(
                incidenciaRepository.count(),
                incidenciaRepository.countByEstado(PENDIENTE),
                incidenciaRepository.countByEstado(ASIGNADA)
                        + incidenciaRepository.countByEstado(EN_PROGRESO)
                        + incidenciaRepository.countByEstado(REABIERTA),
                contarResueltas(),
                incidenciaRepository.countByTecnicoAsignadoIsNull(),
                usuarioRepository.count(),
                usuarioRepository.countByRol(Rol.TECNICO),
                incidenciaRepository.countByPrioridad(BAJA),
                incidenciaRepository.countByPrioridad(MEDIA),
                incidenciaRepository.countByPrioridad(ALTA),
                incidenciaRepository.countByPrioridad(CRITICA)
        );
    }

    public TecnicoDashboardResponse obtenerTecnicoDashboard(String username) {
        long pendientes = incidenciaRepository.countByTecnicoAsignadoAndEstado(username, PENDIENTE)
                + incidenciaRepository.countByTecnicoAsignadoAndEstado(username, ASIGNADA)
                + incidenciaRepository.countByTecnicoAsignadoAndEstado(username, EN_PROGRESO)
                + incidenciaRepository.countByTecnicoAsignadoAndEstado(username, REABIERTA);

        return new TecnicoDashboardResponse(
                incidenciaRepository.countByTecnicoAsignado(username),
                pendientes,
                contarResueltasTecnico(username),
                incidenciaRepository.countByTecnicoAsignadoAndPrioridad(username, ALTA),
                incidenciaRepository.countByTecnicoAsignadoAndPrioridad(username, CRITICA)
        );
    }

    public UsuarioDashboardResponse obtenerUsuarioDashboard(String username) {
        return new UsuarioDashboardResponse(
                incidenciaRepository.countByUsuarioReporta(username),
                incidenciaRepository.countByUsuarioReportaAndEstado(username, PENDIENTE),
                incidenciaRepository.countByUsuarioReportaAndEstado(username, ASIGNADA)
                        + incidenciaRepository.countByUsuarioReportaAndEstado(username, EN_PROGRESO)
                        + incidenciaRepository.countByUsuarioReportaAndEstado(username, REABIERTA),
                contarResueltasUsuario(username)
        );
    }

    private long contarResueltas() {
        return incidenciaRepository.countByEstado(RESUELTA)
                + incidenciaRepository.countByEstado(RESUELTA_POR_TECNICO)
                + incidenciaRepository.countByEstado(CONFIRMADA);
    }

    private long contarResueltasTecnico(String username) {
        return incidenciaRepository.countByTecnicoAsignadoAndEstado(username, RESUELTA)
                + incidenciaRepository.countByTecnicoAsignadoAndEstado(username, RESUELTA_POR_TECNICO)
                + incidenciaRepository.countByTecnicoAsignadoAndEstado(username, CONFIRMADA);
    }

    private long contarResueltasUsuario(String username) {
        return incidenciaRepository.countByUsuarioReportaAndEstado(username, RESUELTA)
                + incidenciaRepository.countByUsuarioReportaAndEstado(username, RESUELTA_POR_TECNICO)
                + incidenciaRepository.countByUsuarioReportaAndEstado(username, CONFIRMADA);
    }
}
