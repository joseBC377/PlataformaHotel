package com.example.hotel.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Usuario;
import com.example.hotel.services.UsuarioService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "api/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsuarioRestController {
    private final UsuarioService service;

    @GetMapping(value = "lista")
    public List<Usuario> seletAllUsers() {
        return service.selectAll();
    }

    @GetMapping(value = "lista/{id}")
    public Usuario selectIdUser(@PathVariable Integer id) {
        return service.selectId(id);
    }

    @PostMapping(value = "insertar")
    public Usuario insertIdUser(@RequestBody Usuario usuario) {
        return service.insertUsuario(usuario);
    }

    @PutMapping(value = "actualizar/{id}")
    public Usuario updateIdUser(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id); //asi haga lo que haga tendra el mismo id
        return service.updateUsuario(id, usuario);
    }

    @DeleteMapping(value = "eliminar/{id}")
    public ResponseEntity<String> deleteIdUser(@PathVariable Integer id) {
        service.deleteUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente Id :" + id);
    }
}
