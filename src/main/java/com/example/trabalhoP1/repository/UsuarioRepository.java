package com.example.trabalhoP1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trabalhoP1.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método essencial para o Spring Security encontrar o usuário na hora do login
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);
}