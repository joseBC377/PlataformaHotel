package com.example.hotel.controllers;
import java.util.List;


import java.util.Optional;

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

import com.example.hotel.entities.Reserva_habitacion;
import com.example.hotel.services.Reserva_habitacionService;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("api/reservas/habitaciones")
@AllArgsConstructor
public class Reserva_habitacionRestController {
        private Reserva_habitacionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reserva_habitacion> selectAllReservas_habitacion() {
        return service.selectAllReserva();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva_habitacion> getReservaById(@PathVariable Integer id) {
        return service.selectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Reserva_habitacion> insUpdReservaHabit(@RequestBody Reserva_habitacion reserva_habitacion) {
        return ResponseEntity.ok(service.insUpdReservaHabit(reserva_habitacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva_habitacion> actualizar(@PathVariable Integer id, @RequestBody Reserva_habitacion reserva_habitActualizada) {
        Optional<Reserva_habitacion> reservaExistente = service.selectById(id);
        if (reservaExistente.isPresent()) {
            reserva_habitActualizada.setId(id);
            return ResponseEntity.ok(service.insUpdReservaHabit(reserva_habitActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReserva(@PathVariable Integer id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Reserva de la habitación eliminada correctamente.");
        } else {
            return ResponseEntity.status(404).body("Reserva de la habitación no encontrada.");
        }
    }   
}
