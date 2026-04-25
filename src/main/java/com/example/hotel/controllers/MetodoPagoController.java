package com.example.hotel.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.entities.MetodoPago;
import com.example.hotel.services.MetodoPagoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/metodoPago", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService service;

    @GetMapping
    public List<MetodoPago> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodoPago> guardar(@Valid @RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(service.guardar(metodoPago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody MetodoPago metodoPago) {
        return service.actualizar(id, metodoPago)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}