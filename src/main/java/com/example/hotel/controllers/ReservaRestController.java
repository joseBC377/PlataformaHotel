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

import com.example.hotel.entities.Reserva;
import com.example.hotel.services.ReservaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/reservas")
@AllArgsConstructor
public class ReservaRestController {

    private ReservaService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reserva> selectAllReservas() {
        return service.selectAllReserva();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Integer id) {
        return service.selectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Reserva> insUpdReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.ok(service.insUpdReserva(reserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Integer id,@Valid @RequestBody Reserva reservaActualizada) {
        Optional<Reserva> reservaExistente = service.selectById(id);
        if (reservaExistente.isPresent()) {
            reservaActualizada.setId(id);
            return ResponseEntity.ok(service.insUpdReserva(reservaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReserva(@PathVariable Integer id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Reserva eliminada correctamente.");
        } else {
            return ResponseEntity.status(404).body("Reserva no encontrada.");
        }
    }    
}
