package com.telnetcom.backend.controller;

import com.telnetcom.backend.dto.ReporteIncidenciasResponse;
import com.telnetcom.backend.service.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/incidencias")
    public ReporteIncidenciasResponse reporteIncidencias(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false) String tecnico,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin
    ) {
        return reporteService.generarReporte(
                estado,
                prioridad,
                tecnico,
                usuario,
                fechaInicio,
                fechaFin
        );
    }

    @GetMapping(value = "/incidencias/csv", produces = "text/csv")
    public ResponseEntity<String> reporteIncidenciasCsv(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false) String tecnico,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaInicio,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaFin
    ) {
        String csv = reporteService.generarCsv(
                estado,
                prioridad,
                tecnico,
                usuario,
                fechaInicio,
                fechaFin
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=incidencias.csv"
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
