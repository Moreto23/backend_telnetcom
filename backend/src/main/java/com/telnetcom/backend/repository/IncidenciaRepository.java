package com.telnetcom.backend.repository;

import com.telnetcom.backend.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IncidenciaRepository
        extends JpaRepository<Incidencia, Long>, JpaSpecificationExecutor<Incidencia> {

        List<Incidencia> findByUsuarioReporta(
                String usuarioReporta
        );

        List<Incidencia> findByTecnicoAsignado(
                String tecnicoAsignado
        );

        List<Incidencia> findByEstado(
                String estado
        );

        List<Incidencia> findByTecnicoAsignadoAndEstado(
                String tecnicoAsignado,
                String estado
        );

        long countByEstado(String estado);

        long countByPrioridad(String prioridad);

        long countByTecnicoAsignado(String tecnicoAsignado);

        long countByTecnicoAsignadoAndEstado(
                String tecnicoAsignado,
                String estado
        );

        long countByTecnicoAsignadoAndPrioridad(
                String tecnicoAsignado,
                String prioridad
        );

        long countByUsuarioReporta(String usuarioReporta);

        long countByUsuarioReportaAndEstado(
                String usuarioReporta,
                String estado
        );

        long countByTecnicoAsignadoIsNull();

}
