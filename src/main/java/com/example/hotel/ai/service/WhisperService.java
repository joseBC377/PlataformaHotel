package com.example.hotel.ai.service;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WhisperService {

    private static final String WHISPER_MODEL =
            "whisper-large-v3-turbo";

    private final RestClient restClient;

    public WhisperService(
            @Value("${groq.api.key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .defaultHeader(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .build();
    }


    public String transcribir(MultipartFile audio) {

        // ==========================================
        // 1. VALIDAR ARCHIVO
        // ==========================================

        if (audio == null || audio.isEmpty()) {
            throw new RuntimeException(
                    "No se recibió ningún archivo de audio."
            );
        }


        try {

            // ==========================================
            // 2. PREPARAR AUDIO
            // ==========================================

            ByteArrayResource audioResource =
                    new ByteArrayResource(
                            audio.getBytes()
                    ) {

                        @Override
                        public String getFilename() {

                            String nombre =
                                    audio.getOriginalFilename();

                            if (nombre == null
                                    || nombre.isBlank()) {

                                return "audio.webm";
                            }

                            return nombre;
                        }
                    };


            // ==========================================
            // 3. MULTIPART
            // ==========================================

            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add(
                    "file",
                    audioResource
            );

            body.add(
                    "model",
                    WHISPER_MODEL
            );

            // Español
            body.add(
                    "language",
                    "es"
            );

            body.add(
                    "response_format",
                    "json"
            );

            body.add(
                    "temperature",
                    "0"
            );


            // ==========================================
            // 4. ENVIAR A GROQ WHISPER
            // ==========================================

            JsonNode response =
                    restClient
                            .post()
                            .uri(
                                    "/audio/transcriptions"
                            )
                            .contentType(
                                    MediaType.MULTIPART_FORM_DATA
                            )
                            .body(body)
                            .retrieve()
                            .body(
                                    JsonNode.class
                            );


            // ==========================================
            // 5. VALIDAR RESPUESTA
            // ==========================================

            if (response == null
                    || !response.has("text")) {

                throw new RuntimeException(
                        "Whisper no devolvió una transcripción válida."
                );
            }


            String texto =
                    response
                            .get("text")
                            .asText()
                            .trim();


            if (texto.isBlank()) {

                throw new RuntimeException(
                        "No se pudo detectar voz en el audio."
                );
            }


            System.out.println(
                    "[WHISPER] Transcripción: "
                            + texto
            );


            return texto;


        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al procesar el audio: "
                            + e.getMessage(),
                    e
            );
        }
    }
}