package com.exemplo.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.projeto.model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
   
}