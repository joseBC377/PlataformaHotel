package com.example.hotel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.hotel.Services.ServicioService;
import com.example.hotel.entities.Servicio;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/servicio",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ServicioRestController {
    @Autowired
    private final ServicioService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  List<Servicio> selectAllServicios(){
        return service.sellectAllServicios();
    }
    // Buscar con endpoints habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        return service.getServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Insertar con endpoints una nueva habitación
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servicio> insertServicio(@RequestBody Servicio servicio) {
        Servicio nuevaServicio = service.insertServicio(servicio);
        return ResponseEntity.ok(nuevaServicio);
    }

    // Actualizar  con endpoints  una habitación existente
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        return service.updateServicio(id, servicio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar  con endpoints una habitación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        if (service.deleteServicio(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
