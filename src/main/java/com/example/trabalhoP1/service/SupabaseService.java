package com.example.trabalhoP1.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    public String uploadImagem(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // Gera um nome único para o arquivo não substituir outro sem querer
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename().replace(" ", "_");
            String bucketName = "capas";
            String endpoint = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(supabaseKey);
            headers.setContentType(MediaType.valueOf(file.getContentType()));

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

            // Dispara o arquivo para o Supabase
            restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            // Retorna o link público da imagem salva
            return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;

        } catch (Exception e) {
            System.err.println("Erro ao fazer upload da imagem: " + e.getMessage());
            return null;
        }
    }
}