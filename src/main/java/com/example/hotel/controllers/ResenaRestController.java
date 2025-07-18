package com.example.hotel.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Resena;
import com.example.hotel.services.ResenaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/resena",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ResenaRestController {

    @Autowired
    private final ResenaService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Resena> selectAllResenas(){
        return service.selectAllResenas();
    }
    // Buscar con endpoints habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Integer id) {
        return service.getResenaById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Insertar con endpoints una nueva habitación
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resena> insertResena(@Valid @RequestBody Resena resena) {
        Resena nuevaResena = service.insertResena(resena);
        return ResponseEntity.ok(nuevaResena);
    }

    // Actualizar  con endpoints  una habitación existente
    @PutMapping("/{id}")
    public ResponseEntity <Void> updateResena(@PathVariable Integer id,@Valid @RequestBody Resena resena) {
        service.updateResena(id, resena);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // Eliminar  con endpoints una habitación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Integer id) {
        if (service.deleteResena(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
