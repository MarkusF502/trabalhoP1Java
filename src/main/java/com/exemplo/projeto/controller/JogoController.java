package com.exemplo.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.exemplo.projeto.model.Jogo;
import com.exemplo.projeto.repository.JogoRepository;

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

    @PostMapping("/salvar")
    public String salvarJogo(Jogo jogo) {
        jogoRepository.save(jogo);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editarJogo(@PathVariable("id") Long id, Model model) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do jogo inválido: " + id));
        model.addAttribute("jogo", jogo);
        
        return "cadastro"; 
    }

    @GetMapping("/excluir/{id}")
    public String excluirJogo(@PathVariable("id") Long id) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do jogo inválido: " + id));
        
        jogoRepository.delete(jogo);
        
        return "redirect:/";
    }
}