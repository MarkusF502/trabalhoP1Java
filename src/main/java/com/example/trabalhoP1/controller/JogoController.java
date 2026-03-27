package com.example.trabalhoP1.controller;

import com.example.trabalhoP1.model.Jogo;
import com.example.trabalhoP1.repository.JogoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        
        model.addAttribute("jogos", listaDeJogos);
        
        return "index";
    }

   @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("jogo", new Jogo());
        return "cadastro";
    }

    @GetMapping("/deletar/{id}")
    public String deletarJogo(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        jogoRepository.deleteById(id); 
        return "redirect:/"; 
    }

    @PostMapping("/salvar")
    public String salvarJogo(Jogo jogo) {
       
        jogoRepository.save(jogo);
        
        return "redirect:/";
    }
}