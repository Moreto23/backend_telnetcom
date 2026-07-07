package com.telnetcom.backend.controller;

import com.telnetcom.backend.dto.LoginRequest;
import com.telnetcom.backend.dto.LoginResponse;

import com.telnetcom.backend.model.Usuario;

import com.telnetcom.backend.repository.UsuarioRepository;

import com.telnetcom.backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200"
})
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        Optional<Usuario> usuarioOptional =
                usuarioRepository.findByUsername(request.getUsername());

        if(usuarioOptional.isPresent()){

            Usuario usuario = usuarioOptional.get();

            if(passwordValida(usuario, request.getPassword())){

                actualizarPasswordPlanoSiCorresponde(usuario, request.getPassword());

                // GENERAR TOKEN
                String token =
                        jwtService.generateToken(usuario.getUsername());

                return new LoginResponse(

                        usuario.getUsername(),

                        usuario.getRol().name(),

                        "Login correcto",

                        token
                );

            }

        }

        return new LoginResponse(

                "",

                "",

                "Credenciales incorrectas",

                ""

        );

    }

    private boolean passwordValida(Usuario usuario, String passwordIngresada) {
        String passwordGuardada = usuario.getPassword();

        if (passwordGuardada == null || passwordIngresada == null) {
            return false;
        }

        if (passwordGuardada.startsWith("$2a$")
                || passwordGuardada.startsWith("$2b$")
                || passwordGuardada.startsWith("$2y$")) {
            return passwordEncoder.matches(passwordIngresada, passwordGuardada);
        }

        return passwordGuardada.equals(passwordIngresada);
    }

    private void actualizarPasswordPlanoSiCorresponde(
            Usuario usuario,
            String passwordIngresada
    ) {
        String passwordGuardada = usuario.getPassword();

        if (passwordGuardada != null
                && !passwordGuardada.startsWith("$2a$")
                && !passwordGuardada.startsWith("$2b$")
                && !passwordGuardada.startsWith("$2y$")) {
            usuario.setPassword(passwordEncoder.encode(passwordIngresada));
            usuarioRepository.save(usuario);
        }
    }

}
