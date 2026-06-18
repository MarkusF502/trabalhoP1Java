package com.example.trabalhoP1.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título não pode ficar em branco")
    private String titulo;

    @NotEmpty(message = "Selecione pelo menos uma plataforma")
    @ElementCollection 
    private List<String> plataforma;

    @NotNull(message = "O ano de lançamento é obrigatório")
    @Min(value = 1958, message = "O ano não pode ser menor que 1958")
    private Integer anoLancamento;

    @NotEmpty(message = "Selecione pelo menos um gênero")
    @ElementCollection
    private List<String> genero;

    public Jogo() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<String> getPlataforma() { return plataforma; }
    public void setPlataforma(List<String> plataforma) { this.plataforma = plataforma; }

    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }

    public List<String> getGenero() { return genero; }
    public void setGenero(List<String> genero) { this.genero = genero; }
}