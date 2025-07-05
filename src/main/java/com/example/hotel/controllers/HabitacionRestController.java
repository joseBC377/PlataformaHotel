package com.example.hotel.controllers;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.services.HabitacionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "api/habitacion",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HabitacionRestController {
    //Inyeccion de dependencia 
    private final HabitacionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Habitacion> selectAllHabitacions(){
        return service.selectAllHabitacions();
    }
    // Buscar con endpoints habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> getHabitacionById(@PathVariable Integer id) {
        return service.getHabitacionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Insertar con endpoints una nueva habitación
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Habitacion> insertHabitacion(@Valid @RequestBody Habitacion habitacion) {
        Habitacion nuevaHabitacion = service.insertHabitacion(habitacion);
        return ResponseEntity.ok(nuevaHabitacion);
    }

    // Actualizar  con endpoints  una habitación existente
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> updateHabitacion(@PathVariable Integer id,@Valid @RequestBody Habitacion habitacion) {
        return service.updateHabitacion(id, habitacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar  con endpoints una habitación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabitacion(@PathVariable Integer id) {
        if (service.deleteHabitacion(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
