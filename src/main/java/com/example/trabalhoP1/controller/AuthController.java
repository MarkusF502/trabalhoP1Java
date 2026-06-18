package com.example.trabalhoP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.trabalhoP1.model.Usuario;
import com.example.trabalhoP1.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        // Enviando as mesmas plataformas usadas nos jogos
        model.addAttribute("todasPlataformas", List.of("PC", "PlayStation 5", "PlayStation 4", "Xbox Series X/S", "Xbox One", "Nintendo Switch", "Mobile"));
        return "registro";
    }

    @PostMapping("/salvar-usuario")
    public String salvarUsuario(@Valid Usuario usuario, BindingResult result, Model model) {
        
        // Verifica se o e-mail já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Este e-mail já está em uso.");
        }

        if (result.hasErrors()) {
            model.addAttribute("todasPlataformas", List.of("PC", "PlayStation 5", "PlayStation 4", "Xbox Series X/S", "Xbox One", "Nintendo Switch", "Mobile"));
            return "registro";
        }

        // Criptografa a senha antes de salvar no banco
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        // Redireciona para o login com uma mensagem de sucesso
        return "redirect:/login?sucesso";
    }
}