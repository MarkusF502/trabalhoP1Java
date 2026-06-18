package com.example.trabalhoP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trabalhoP1.model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    
    boolean existsByTituloIgnoreCase(String titulo);
}