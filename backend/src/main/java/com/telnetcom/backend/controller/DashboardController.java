package com.telnetcom.backend.controller;

import com.telnetcom.backend.dto.AdminDashboardResponse;
import com.telnetcom.backend.dto.TecnicoDashboardResponse;
import com.telnetcom.backend.dto.UsuarioDashboardResponse;
import com.telnetcom.backend.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    public AdminDashboardResponse obtenerAdminDashboard() {
        return dashboardService.obtenerAdminDashboard();
    }

    @GetMapping("/tecnico/{username}")
    public TecnicoDashboardResponse obtenerTecnicoDashboard(
            @PathVariable String username
    ) {
        return dashboardService.obtenerTecnicoDashboard(username);
    }

    @GetMapping("/usuario/{username}")
    public UsuarioDashboardResponse obtenerUsuarioDashboard(
            @PathVariable String username
    ) {
        return dashboardService.obtenerUsuarioDashboard(username);
    }
}
