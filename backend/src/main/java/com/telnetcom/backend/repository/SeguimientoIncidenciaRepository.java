package com.telnetcom.backend.repository;

import com.telnetcom.backend.model.SeguimientoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguimientoIncidenciaRepository
        extends JpaRepository<SeguimientoIncidencia, Long> {

    List<SeguimientoIncidencia> findByIncidenciaIdOrderByFechaRegistroAsc(Long incidenciaId);
}
