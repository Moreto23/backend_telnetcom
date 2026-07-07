package com.telnetcom.backend.service;

import com.telnetcom.backend.dto.ActualizarUsuarioRequest;
import com.telnetcom.backend.dto.CrearUsuarioRequest;
import com.telnetcom.backend.model.Rol;
import com.telnetcom.backend.model.Usuario;
import com.telnetcom.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarTecnicos() {
        return usuarioRepository.findByRol(Rol.TECNICO);
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));
    }

    public Usuario crear(CrearUsuarioRequest request) {
        String username = request.getUsername().trim();

        if (usuarioRepository.existsByUsername(username)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El username ya existe"
            );
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.valueOf(request.getRol()));

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, ActualizarUsuarioRequest request) {
        Usuario usuario = obtenerPorId(id);

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            String nuevoUsername = request.getUsername().trim();

            if (!nuevoUsername.equals(usuario.getUsername())
                    && usuarioRepository.existsByUsername(nuevoUsername)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El username ya existe"
                );
            }

            usuario.setUsername(nuevoUsername);
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRol() != null && !request.getRol().isBlank()) {
            usuario.setRol(Rol.valueOf(request.getRol()));
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado"
            );
        }

        usuarioRepository.deleteById(id);
    }
}
