package com.example.hotel.repositories;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
//import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.annotation.Rollback;

import com.example.hotel.util.Rol;
import com.example.hotel.util.RolEstadoPago;
import com.example.hotel.util.RolMetodoPago;
import com.example.hotel.util.RolReserva;
import com.example.hotel.entities.MetodoPago;
//import com.example.hotel.util.RolMetodoPago;
//import com.example.hotel.HotelApplication;
import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.Usuario;

import jakarta.transaction.Transactional;

// @ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback


@SpringBootTest
public class PagoRepositoryTest {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Test
    @Transactional
    public void insertarPago() {


        // 1. Crear y GUARDAR el usuario primero
        Usuario usuario = new Usuario();
        usuario.setNombre_usuario("Juan");
        usuario.setApellido_paterno("Perez"); 
        usuario.setApellido_materno("Gomez"); 
        usuario.setCorreo("juan.perez@hotel.com"); 
        usuario.setPassword("password123"); 
        usuario.setTelefono("987654321"); 
        usuario.setFecha_nacimiento(LocalDate.of(1990, 1, 1)); 
        usuario.setRol(Rol.CLIENT);

        usuario = usuarioRepository.save(usuario); 


        // 2. Simular reserva con el usuario ya guardado
        Reserva reserva = new Reserva();
        reserva.setFechaCreacion(LocalDate.now());        
        reserva.setEstado(RolReserva.CONFIRMADO); 
        reserva.setUsuario(usuario); 

        reserva = reservaRepository.save(reserva);

        MetodoPago metodo = new MetodoPago();
        metodo.setTipo(RolMetodoPago.TARJETA); 
        metodo.setActivo(true);
        metodo.setUltimoscuatrodigitos("1234");
        metodo.setFechaVencimiento("12/26");

        metodo.setUsuario(usuario);

        // 2. GUARDAR el método 
        metodo = metodoPagoRepository.save(metodo);

        // 3. Ahora sí, crear y configurar el Pago
        Pago pago = new Pago();
        pago.setTotal(new BigDecimal("125.4"));
        pago.setEstado_pago(RolEstadoPago.RECHAZADO);
        pago.setFecha_pago(LocalDate.now());
        pago.setReserva(reserva);
        pago.setIgv(new BigDecimal("22.7"));

        // 4. ASIGNAR el método guardado al pago
        pago.setMetodoPago(metodo); 

        // 5. GUARDAR el pago
        Pago pagoGuardar = repository.save(pago);

        assertNotNull(pagoGuardar.getId_pago());
        // assertEquals(RolEstadoPago.RECHAZADO, pagoGuardar.getEstado_pago());
        // assertNotNull(pagoGuardar.getReserva().getId_reserva());

    }

}
