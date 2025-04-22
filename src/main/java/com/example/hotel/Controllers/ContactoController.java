package com.example.hotel.Controllers;

import java.util.List;

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

import com.example.hotel.entities.Contacto;
import com.example.hotel.Services.ContactoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/contacto", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ContactoController {
    private final ContactoService service;

    @GetMapping("lista")
    public List<Contacto> selectContact() {
        return service.selectAll();
    }

    @GetMapping("lista/{id}")
    public Contacto selectContacId(@PathVariable Integer id) {
        return service.selectId(id);
    }

    @PostMapping("insertar")
    public Contacto insertContact(@RequestBody Contacto contacto) {
        return service.insert(contacto);
    }

    @PutMapping("actualizar/{id}")
    public Contacto updateIdContact(@PathVariable Integer id, @RequestBody Contacto contacto) {
        contacto.setId(id);
        return service.updateContact(id, contacto);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Integer id) {
        service.deleteContact(id);
        return ResponseEntity.ok("Usuario eliminado Id : " + id);
    }
}
