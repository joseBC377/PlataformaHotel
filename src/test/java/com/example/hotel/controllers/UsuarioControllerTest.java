package com.example.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.hotel.entities.Usuario;
import com.example.hotel.services.JwtService;
import com.example.hotel.services.UsuarioService;
import com.example.hotel.util.Rol;

@WebMvcTest(controllers = UsuarioRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ContextConfiguration(classes = {UsuarioRestController.class})
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void insertIdUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setNombre_usuario("Jose Jesus");
        usuario.setApellido_paterno("Balcazar");
        usuario.setApellido_materno("Choqque");
        usuario.setTelefono("978152175");
        usuario.setCorreo("jbalcazar377@gmail.com");
        usuario.setPassword("197548636");
        usuario.setFecha_nacimiento(LocalDate.of(2000, 1, 1));
        usuario.setRol(Rol.ADMIN);

        when(service.insertUsuario(any(Usuario.class))).thenReturn(usuario);

        // JSON completo que cumple con los @NotNull y nombres de la entidad
        String jsonContent = """
            {
                "nombre_usuario": "Jose Jesus",
                "apellido_paterno": "Balcazar",
                "apellido_materno": "Choqque",
                "telefono": "978152175",
                "correo": "jbalcazar377@gmail.com",
                "password": "197548636",
                "fecha_nacimiento": "2000-01-01",
                "rol": "ADMIN"
            }""";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuario/insertar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Usamos los nombres correctos de los campos en la entidad
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre_usuario").value("Jose Jesus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido_paterno").value("Balcazar"));
    }
}