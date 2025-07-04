package com.example.hotel.controllers;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Pago;
import com.example.hotel.services.PagoService;
import com.example.hotel.util.Pago_ReservaInfo;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(value = "api/pago", produces = MediaType.APPLICATION_JSON_VALUE)

public class PagoController {


    //inyeccion de dependencia por contructor
    private final PagoService service;
    public PagoController (PagoService service){
        this.service = service;
    }



    @GetMapping("lista")
    public List<Pago> selectPago() {
        return service.listarTodas();
    }
    
    @PostMapping("insertar")
    public Pago insertPago(@Valid @RequestBody Pago pago) {
        return service.insert(pago);
    }

    //Post para guardar tanto la reserva como el pago
    @PostMapping( value = "/completo" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String insertarReservaConPago(@RequestBody Pago_ReservaInfo pago_ReservaInfo) {

        try {
            service.crearReservaPago(pago_ReservaInfo);
            return "Reserva con pago creado";

        } catch (Exception e) {
            return e.getMessage();
        }
        
    }
    

    @GetMapping("lista/{idPago}")
    public Pago selectPagoId(@PathVariable Integer idPago) {
        return service.obtenerPorId(idPago);
    }

    @PutMapping("actualizar/{idPago}")
    public Pago updateIdPago(@PathVariable Integer idPago, @Valid @RequestBody Pago pago) {
        pago.setIdPago(idPago);
        return service.actualizarPago(idPago, pago);
    }

    @DeleteMapping("eliminar/{idPago}")
    public ResponseEntity<String> deletePago(@PathVariable Integer idPago) {
        service.eliminar(idPago);
        return ResponseEntity.ok("Pago eliminado con exito de id : " + idPago);
    }


}
