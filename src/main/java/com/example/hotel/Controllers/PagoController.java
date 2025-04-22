package com.example.hotel.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Pago;
import com.example.hotel.services.PagoService;

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
    public Pago insertPago(@RequestBody Pago pago) {
        return service.insert(pago);
    }

    @GetMapping("lista/{idPago}")
    public Pago selectPagoId(@PathVariable Integer idPago) {
        return service.obtenerPorId(idPago);
    }

    @PutMapping("actualizar/{idPago}")
    public Pago updateIdPago(@PathVariable Integer idPago, @RequestBody Pago pago) {
        pago.setIdPago(idPago);
        return service.actualizarPago(idPago, pago);
    }

    @DeleteMapping("eliminar/{idPago}")
    public ResponseEntity<String> deletePago(@PathVariable Integer idPago) {
        service.eliminar(idPago);
        return ResponseEntity.ok("Pago eliminado con exito de id : " + idPago);
    }


}
