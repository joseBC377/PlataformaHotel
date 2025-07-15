package com.example.hotel.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Usuario;
import com.example.hotel.services.UsuarioService;
import com.example.hotel.util.ConteoRol;

import jakarta.validation.Valid;
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

    @GetMapping(value = "todosCliente")
    public List<Usuario> selectAllClient() {
        return service.selectAllClient();
    }
    
    @GetMapping(value = "TodosUsuarioReserva")
    public List<Usuario> TodosUsuarioReserva() {
        return service.TodosUsuarioReserva();
    }

    @GetMapping(value = "contarUsuariosRol")
    public List<ConteoRol> contarUsuariosRol() {
        return service.contarUsuariosRol();
    }

    @GetMapping(value = "lista")
    public List<Usuario> seletAllUsers() {
        return service.selectAll();
    }

    @GetMapping(value = "lista/{id}")
    public Usuario selectIdUser(@PathVariable Integer id) {
        return service.selectId(id);
    }

    @PostMapping(value = "insertar")
    public Usuario insertIdUser(@Valid @RequestBody Usuario usuario) {
        return service.insertUsuario(usuario);
    }

    @PutMapping(value = "actualizar/{id}")
    public Usuario updateIdUser(@PathVariable Integer id,@Valid @RequestBody Usuario usuario) {
        usuario.setId(id); //asi haga lo que haga tendra el mismo id
        return service.updateUsuario(id, usuario);
    }

    @DeleteMapping(value = "eliminar/{id}")
    public void deleteIdUser(@PathVariable Integer id) {
        service.deleteUsuario(id);
    }


    //----Paginas de acceso para usuarios publicos y privados----

    @GetMapping("/publico")
    public String paginaPublica () {
        return "Pagina publica";
    }

    @GetMapping("/privado")
    public String paginaPrivada () {
        return "Hola ingresastes a la pagina privada";
    }    


}
