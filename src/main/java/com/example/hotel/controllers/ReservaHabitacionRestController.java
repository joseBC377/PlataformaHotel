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

import com.example.hotel.entities.ReservaHabitacion;
import com.example.hotel.services.ReservaHabitacionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping("api/reservas/habitaciones")
@AllArgsConstructor
public class ReservaHabitacionRestController {
        private ReservaHabitacionService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservaHabitacion> selectAllReservas_habitacion() {
        return service.selectAllReserva();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaHabitacion> getReservaById(@PathVariable Integer id) {
        return service.selectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ReservaHabitacion> insUpdReservaHabit(@Valid @RequestBody ReservaHabitacion ReservaHabitacion) {
        return ResponseEntity.ok(service.insUpdReservaHabit(ReservaHabitacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaHabitacion> actualizar(@PathVariable Integer id,@Valid @RequestBody ReservaHabitacion reservahabitActualizada) {
        Optional<ReservaHabitacion> reservaExistente = service.selectById(id);
        if (reservaExistente.isPresent()) {
            reservahabitActualizada.setId(id);
            return ResponseEntity.ok(service.insUpdReservaHabit(reservahabitActualizada));
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
