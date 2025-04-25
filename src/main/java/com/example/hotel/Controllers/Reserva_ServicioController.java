package com.example.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.Reserva_Servicio;
import com.example.hotel.entities.Servicio;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.services.Reserva_ServicioService;

@RestController
@RequestMapping(value = "api/ReservaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
public class Reserva_ServicioController {

    @Autowired
    private ReservaRepository reservaRepository;

    private final Reserva_ServicioService reservaServicioService;

    public Reserva_ServicioController(Reserva_ServicioService reservaServicioService) {
        this.reservaServicioService = reservaServicioService;
    }


    /*

    @PostMapping("insertar")
    public ResponseEntity<Reserva_Servicio> insertarReservaServicio(@RequestBody ReservaServicioDTO dto) {
        // Crear objetos "vacíos" solo con el ID
        Reserva reserva = new Reserva();
        reserva.setId(dto.getId());

        Servicio servicio = new Servicio();
        servicio.setId_servicio(dto.getIdServicio()); // Usando el getter correcto

        // Crear el ID compuesto
        ReservaServicioId id = new ReservaServicioId(dto.getId(), dto.getIdServicio()); // Asegúrate de usar los mismos
                                                                                        // nombres aquí

        // Crear la entidad de relación
        Reserva_Servicio reservaServicio = new Reserva_Servicio();
        reservaServicio.setId(id);
        reservaServicio.setReserva(reserva);
        reservaServicio.setServicio(servicio);

        // Guardar sin necesidad de buscar en la BD
        Reserva_Servicio guardado = reservaServicioService.guardar(reservaServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    */


    
    @GetMapping("lista")
    public List<Reserva_Servicio> selectReserva_Servicios() {
        return reservaServicioService.listarTodas();
    }

   
    

}