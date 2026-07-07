package com.telnetcom.backend.repository;

import com.telnetcom.backend.model.Usuario;
import com.telnetcom.backend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    List<Usuario> findByRol(Rol rol);

    long countByRol(Rol rol);

}
