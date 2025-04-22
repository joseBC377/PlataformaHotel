package com.example.hotel.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Reserva_Servicio;
import com.example.hotel.services.Reserva_ServicioService;

@RestController
@RequestMapping(value = "api/ReservaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
public class Reserva_ServicioController {

     private final Reserva_ServicioService reservaServicioService;

    public Reserva_ServicioController(Reserva_ServicioService reservaServicioService) {
        this.reservaServicioService = reservaServicioService;
    }

/* 
     // Endpoint para guardar una nueva relación Reserva-Servicio
    @PostMapping
    public ResponseEntity<Reserva_Servicio> guardarReservaServicio(@RequestBody Reserva_Servicio reservaServicio) {
        Reserva_Servicio nuevaReservaServicio = reservaServicioService.guardar(reservaServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReservaServicio);
    }

    // Endpoint para listar todas las relaciones Reserva-Servicio
    @GetMapping
    public List<Reserva_Servicio> listarReservaServicio() {
        return reservaServicioService.listar();
    }

*/

}