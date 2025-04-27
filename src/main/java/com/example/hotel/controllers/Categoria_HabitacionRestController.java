package com.example.hotel.controllers;
import java.util.List;

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

import com.example.hotel.entities.Categoria_Habitacion;
import com.example.hotel.services.Categoria_HabitacionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/categoria_habitacion",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class Categoria_HabitacionRestController {
private final Categoria_HabitacionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Categoria_Habitacion> selCategoria_Habitacions(){
        return service.selCategoria_Habitacions();
    }
    // Buscar con endpoints categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria_Habitacion> getCategoriaById(@PathVariable Integer id) {
        return service.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Insertar con endpoints una nueva categoría
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Categoria_Habitacion> insertCategoria(@RequestBody Categoria_Habitacion categoria) {
        return ResponseEntity.ok(service.insertCategoria(categoria));
    }

    // Actualizar con endpoints categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria_Habitacion> updateCategoria(
            @PathVariable Integer id,
            @RequestBody Categoria_Habitacion categoria) {
        return service.updateCategoria(id, categoria)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar con endpoints categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        if (service.deleteCategoria(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
