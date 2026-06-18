package com.example.trabalhoP1.controller;

import java.security.Principal;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.trabalhoP1.model.Jogo;
import com.example.trabalhoP1.repository.JogoRepository;
import com.example.trabalhoP1.repository.UsuarioRepository;
import com.example.trabalhoP1.service.SupabaseService;

import jakarta.validation.Valid;

@Controller
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SupabaseService supabaseService;

    private void carregarListas(Model model) {
        model.addAttribute("todasPlataformas", List.of("PC", "PlayStation 5", "PlayStation 4", "Xbox Series X/S", "Xbox One", "Nintendo Switch", "Mobile"));
        model.addAttribute("todosGeneros", List.of("Ação", "Aventura", "RPG", "Hack and Slash", "Mundo Aberto", "Tiro", "Esportes", "Estratégia", "Sobrevivência"));
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        List<Jogo> listaDeJogos = jogoRepository.findAll();
        model.addAttribute("jogos", listaDeJogos);

        if (principal != null) {
            usuarioRepository.findByEmail(principal.getName()).ifPresent(usuario -> {
                model.addAttribute("nomeUsuario", usuario.getNome());
            });
        }
        return "index";
    }

    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("jogo", new Jogo());
        carregarListas(model);
        return "cadastro";
    }

    // Adicionamos o @RequestParam para receber o arquivo de imagem
    @PostMapping("/salvar")
    public String salvarJogo(@Valid Jogo jogo, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
        
        if (jogo.getId() == null && jogoRepository.existsByTituloIgnoreCase(jogo.getTitulo())) {
            result.rejectValue("titulo", "error.jogo", "Este título já está cadastrado no sistema.");
        }

        int anoAtual = Year.now().getValue();
        if (jogo.getAnoLancamento() != null && jogo.getAnoLancamento() > anoAtual) {
            result.rejectValue("anoLancamento", "error.jogo", "O ano não pode ser maior que o ano atual (" + anoAtual + ").");
        }

        if (result.hasErrors()) {
            carregarListas(model); 
            return "cadastro";     
        }

        // Se houver arquivo na requisição, faz upload e salva a URL no modelo do jogo
        if (!file.isEmpty()) {
            String urlImagem = supabaseService.uploadImagem(file);
            jogo.setUrlCapa(urlImagem);
        } else if (jogo.getId() != null) {
            // Se for edição e não enviou nova imagem, mantém a antiga
            Jogo jogoExistente = jogoRepository.findById(jogo.getId()).orElse(null);
            if (jogoExistente != null) {
                jogo.setUrlCapa(jogoExistente.getUrlCapa());
            }
        }

        jogoRepository.save(jogo);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editarJogo(@PathVariable("id") Long id, Model model) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do jogo inválido: " + id));
        
        model.addAttribute("jogo", jogo);
        carregarListas(model); 
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