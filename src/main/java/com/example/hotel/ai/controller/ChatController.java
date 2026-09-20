package com.example.hotel.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.ai.dto.ChatRequest;
import com.example.hotel.ai.dto.ChatResponse;
import com.example.hotel.ai.service.ChatAIService;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final ChatAIService chatAIService;

    public ChatController(ChatAIService chatAIService) {
        this.chatAIService = chatAIService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request) {

        String respuesta = chatAIService.responder(
                request.getMensaje()
        );

        return ResponseEntity.ok(
                new ChatResponse(respuesta)
        );
    }
}