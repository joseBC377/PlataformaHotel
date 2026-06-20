package com.example.hotel.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.entities.ReservaServicio;
import com.example.hotel.services.ReservaServicioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/reserva-servicio", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReservaServicioController {

    private final ReservaServicioService reservaServicioService;

    @GetMapping("/lista")
    public List<ReservaServicio> listar() {
        return reservaServicioService.listarTodas();
    }

    @PostMapping("/insertar")
    public ResponseEntity<ReservaServicio> insertar(@Valid @RequestBody ReservaServicio reservaServicio) {
        ReservaServicio guardado = reservaServicioService.crearRelacion(reservaServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/editar/{idReserva}/{idServicio}")
    public ResponseEntity<ReservaServicio> editar(
            @PathVariable Integer idReserva,
            @PathVariable Integer idServicio,
            @Valid @RequestBody ReservaServicio nuevaRelacion) {

        ReservaServicio actualizado = reservaServicioService.actualizarRelacion(idReserva, idServicio, nuevaRelacion);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/eliminar/{idReserva}/{idServicio}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer idReserva,
            @PathVariable Integer idServicio) {

        reservaServicioService.eliminarRelacion(idReserva, idServicio);
        return ResponseEntity.noContent().build();
    }
}