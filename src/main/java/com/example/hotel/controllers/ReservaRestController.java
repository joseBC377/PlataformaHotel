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

import com.example.hotel.DTOS.HistorialReservaResponse;
import com.example.hotel.DTOS.ReservaCompletaRequest;
import com.example.hotel.entities.Reserva;
import com.example.hotel.services.ReservaCompletaService;
import com.example.hotel.services.ReservaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/reservas")
@AllArgsConstructor
public class ReservaRestController {

    private ReservaService service;
    private ReservaCompletaService reservaCompletaService; // nuevo

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reserva> selectAllReservas() {
        return service.selectAllReserva();
    }

    @GetMapping("/historial/{idUsuario}")
    public ResponseEntity<List<HistorialReservaResponse>> historialPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(reservaCompletaService.obtenerHistorialPorUsuario(idUsuario));
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

    @PostMapping("/completa")
    public ResponseEntity<?> crearReservaCompleta(@RequestBody ReservaCompletaRequest req) {
        try {
            Reserva reserva = reservaCompletaService.crearReservaCompleta(req);
            return ResponseEntity.ok(reserva);
        } catch (EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Reserva reservaActualizada) {
        Optional<Reserva> optional = service.selectById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reserva existente = optional.get();
        existente.setFechaCreacion(reservaActualizada.getFechaCreacion());
        existente.setUsuario(reservaActualizada.getUsuario());
        existente.setEstado(reservaActualizada.getEstado());

        return ResponseEntity.ok(service.insUpdReserva(existente));
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
