package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.hotel.entities.Rol;
import com.example.hotel.entities.Usuario;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UsuarioRepositoriesTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void insertarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setApellido("Prueba");
        usuario.setTelefono("123456789");
        usuario.setCorreo("Test@Gmail.com");
        usuario.setPassword("Test12345");
        usuario.setRol(Rol.ADMIN);
        // no se pone el id manual yaque el hibernate lo genera automaticamente
        Usuario usuarioGuardado = repository.save(usuario);
        // Verificar que el usuario no sea nulo y tenga id
        assertNotNull(usuarioGuardado.getId());
        assertEquals("Test", usuarioGuardado.getNombre());
        //actualizacion
    }
}
