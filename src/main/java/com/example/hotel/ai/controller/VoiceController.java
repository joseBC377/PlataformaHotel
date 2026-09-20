package com.example.hotel.ai.controller;

import com.example.hotel.ai.service.ChatAIService;
import com.example.hotel.ai.service.WhisperService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class VoiceController {

    private final WhisperService whisperService;
    private final ChatAIService chatAIService;

    public VoiceController(
            WhisperService whisperService,
            ChatAIService chatAIService) {

        this.whisperService = whisperService;
        this.chatAIService = chatAIService;
    }


    @PostMapping(
            value = "/voice",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, Object>> procesarVoz(
            @RequestParam("audio") MultipartFile audio) {

        Map<String, Object> resultado =
                new LinkedHashMap<>();

        try {

            // ==========================================
            // 1. AUDIO -> WHISPER
            // ==========================================

            String transcripcion =
                    whisperService.transcribir(audio);

            System.out.println(
                    "[VOICE] Usuario dijo: "
                            + transcripcion
            );


            // ==========================================
            // 2. TEXTO -> ASISTENTE
            // ==========================================

            String respuesta =
                    chatAIService.responder(
                            transcripcion
                    );


            // ==========================================
            // 3. RESPUESTA
            // ==========================================

            resultado.put(
                    "transcripcion",
                    transcripcion
            );

            resultado.put(
                    "respuesta",
                    respuesta
            );

            return ResponseEntity.ok(
                    resultado
            );


        } catch (Exception e) {

            resultado.put(
                    "error",
                    true
            );

            resultado.put(
                    "mensaje",
                    e.getMessage()
            );

            return ResponseEntity
                    .badRequest()
                    .body(resultado);
        }
    }
}