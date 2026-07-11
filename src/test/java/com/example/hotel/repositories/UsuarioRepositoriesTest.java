package com.example.hotel.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate; 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.annotation.Rollback;
import com.example.hotel.entities.Usuario;
import com.example.hotel.util.Rol;
import jakarta.transaction.Transactional;

@SpringBootTest 
@Transactional
@Rollback
public class UsuarioRepositoriesTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void insertarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre_usuario("Test");
        usuario.setApellido_paterno("Perez"); 
        usuario.setApellido_materno("Prueba");
        usuario.setTelefono("123456789");
        usuario.setCorreo("test@gmail.com"); 
        usuario.setPassword("Test12345");
        usuario.setRol(Rol.ADMIN);
        usuario.setFecha_nacimiento(LocalDate.of(2000, 1, 1)); 

        Usuario usuarioGuardado = repository.save(usuario);

        assertNotNull(usuarioGuardado.getId_usuario());
        assertEquals("Test", usuarioGuardado.getNombre_usuario());
    }
}