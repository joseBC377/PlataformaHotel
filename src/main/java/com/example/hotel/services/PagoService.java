package com.example.hotel.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.MetodoPago;
import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.MetodoPagoRepository;
import com.example.hotel.repositories.PagoRepository;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.util.Pago_ReservaInfo;
import com.example.hotel.util.RolEstadoPago;
import com.example.hotel.util.RolMetodoPago;
import com.example.hotel.util.RolReserva;

import jakarta.transaction.Transactional;

@Service
public class PagoService {

    private final PagoRepository repository;
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public PagoService(PagoRepository repository,
                       ReservaRepository reservaRepository,
                       UsuarioRepository usuarioRepository,
                       MetodoPagoRepository metodoPagoRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<Pago> listarTodas() {
        return repository.findAll();
    }

    public Pago insert(Pago pago) {
        return repository.save(pago);
    }

    public Pago obtenerPorId(Integer idPago) {
        return repository.findById(idPago).orElse(null);
    }

    public Pago actualizarPago(Integer idPago, Pago pago) {
        if (!repository.existsById(idPago)) {
            throw new RuntimeException("No se encontró el id " + idPago);
        }
        pago.setId_pago(idPago);
        return repository.save(pago);
    }

    public void eliminar(Integer idPago) {
        if (!repository.existsById(idPago)) {
            throw new RuntimeException("No existe el pago con id " + idPago);
        }
        repository.deleteById(idPago);
    }


    @Transactional
    public void crearReservaPago(Pago_ReservaInfo info) {

        // 1. Buscar usuario
        Usuario usuario = usuarioRepository.buscarPorId(info.id_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear reserva
        Reserva reserva = new Reserva();
        reserva.setFechaCreacion(LocalDate.now());
        reserva.setUsuario(usuario);
        reserva.setEstado(RolReserva.PENDIENTE);

        Reserva reservaGuardada = reservaRepository.save(reserva);

        // 3. CONVERTIR String → ENUM
        RolMetodoPago rolMetodo;
        try {
            rolMetodo = RolMetodoPago.valueOf(info.metodo_pago().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Método de pago inválido: " + info.metodo_pago());
        }

        // 4. Buscar método de pago
        MetodoPago metodoPago = metodoPagoRepository
                .findByTipo(rolMetodo)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

        // 5. CONVERTIR estado String → ENUM
        RolEstadoPago estado;
        try {
            estado = RolEstadoPago.valueOf(info.estado_pago().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Estado de pago inválido: " + info.estado_pago());
        }

        // 6. Crear pago
        Pago pago = new Pago();
        pago.setTotal(info.total());
        pago.setIgv(info.total().multiply(new java.math.BigDecimal("0.18")));
        pago.setEstado_pago(estado);
        pago.setFecha_pago(LocalDate.now());
        pago.setReserva(reservaGuardada);
        pago.setMetodoPago(metodoPago);

        repository.save(pago);
    }
}