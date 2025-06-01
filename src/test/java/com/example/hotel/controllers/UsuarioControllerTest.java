package com.example.hotel.controllers;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.hotel.entities.Usuario;
import com.example.hotel.services.UsuarioService;
import com.example.hotel.util.Rol;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean //me tomo tiemmpo pero es en lugar de mockbean
    private UsuarioService service;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void insertIdUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jose Jesus");
        usuario.setApellido("Balcazar Choqque");
        usuario.setTelefono("978152175");
        usuario.setCorreo("jbalcazar377@gmail.com");
        usuario.setPassword("197548636");
        usuario.setRol(Rol.ADMIN);

        // simulación del comportamiento service
        when(service.insertUsuario(usuario)).thenReturn(usuario);
        // realizar el POST
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/usuario/insertar").contentType(MediaType.APPLICATION_JSON).content(
                        "{\"id\":1,\"nombre\":\"Jose Jesus\",\"apellido\":\"Balcazar Choqque\",\"telefono\":\"978152175\",\"correo\":\"jbalcazar377@gmail.com\",\"password\":\"197548636\",\"rol\":\"ADMIN\"}").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Jose Jesus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Balcazar Choqque"));
    }

}
