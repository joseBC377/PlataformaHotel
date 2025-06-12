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

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.services.CategoriaHabitacionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/categoriaHabitacion",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CategoriaHabitacionRestController {
private final CategoriaHabitacionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<CategoriaHabitacion> selCategoria_Habitacions(){
        return service.selCategoriaHabitacions();
    }
    // Buscar con endpoints categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaHabitacion> getCategoriaById(@PathVariable Integer id) {
        return service.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Insertar con endpoints una nueva categoría
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoriaHabitacion> insertCategoria(@RequestBody CategoriaHabitacion categoria) {
        return ResponseEntity.ok(service.insertCategoria(categoria));
    }

    // Actualizar con endpoints categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaHabitacion> updateCategoria(
            @PathVariable Integer id,
            @RequestBody CategoriaHabitacion categoria) {
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
