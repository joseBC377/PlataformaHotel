package com.example.hotel.services;

import com.example.hotel.DTOS.*;
import com.example.hotel.entities.*;
import com.example.hotel.repositories.*;
import com.example.hotel.util.RolEstadoPago;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolReserva;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaCompletaService {

    private final ReservaRepository reservaRepository;
    private final ReservaHabitacionRepository reservaHabitacionRepository;
    private final ReservaServicioRepository reservaServicioRepository;
    private final PagoRepository pagoRepository;
    private final HabitacionRepository habitacionRepository;
    private final ServicioRepository servicioRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Reserva crearReservaCompleta(ReservaCompletaRequest req) {

        // 1. Validar y traer usuario
        Usuario usuario = usuarioRepository.findById(req.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 2. Crear la reserva base
        Reserva reserva = new Reserva();
        reserva.setFechaCreacion(LocalDate.now());
        reserva.setEstado(RolReserva.CONFIRMADO);
        reserva.setUsuario(usuario);
        reserva = reservaRepository.save(reserva);

        // 3. Procesar cada habitación
        for (DetalleHabitacionRequest d : req.getHabitaciones()) {
            Habitacion habitacion = habitacionRepository.findById(d.getIdHabitacion())
                    .orElseThrow(() -> new EntityNotFoundException("Habitación no encontrada: " + d.getIdHabitacion()));

            if (habitacion.getEstado() != RolHabitacion.DISPONIBLE) {
                throw new IllegalStateException("La habitación " + habitacion.getNombre_habitacion() + " ya no está disponible");
            }

            ReservaHabitacion rh = new ReservaHabitacion();
            rh.setReserva(reserva);
            rh.setHabitacion(habitacion);
            rh.setFechaInicio(d.getFechaInicio());
            rh.setFechaFin(d.getFechaFin());
            rh.setPrecioUnitario(d.getPrecioUnitario());
            reservaHabitacionRepository.save(rh);

            habitacion.setEstado(RolHabitacion.OCUPADA);
            habitacionRepository.save(habitacion);
        }

        // 4. Procesar cada servicio adicional
        for (DetalleServicioRequest d : req.getServicios()) {
            Servicio servicio = servicioRepository.findById(d.getIdServicio())
                    .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado: " + d.getIdServicio()));

            ReservaServicio rs = new ReservaServicio();
            ReservaServicioId rsId = new ReservaServicioId(reserva.getId_reserva(), servicio.getIdServicio());
            rs.setId(rsId);
            rs.setReserva(reserva);
            rs.setServicio(servicio);
            rs.setSubtotal(d.getSubtotal());
            reservaServicioRepository.save(rs);
        }

        // 5. Crear el método de pago del usuario
        MetodoPago metodoPago = new MetodoPago();
        metodoPago.setTipo(req.getPago().getMetodoPago().getTipo());
        metodoPago.setUltimoscuatrodigitos(req.getPago().getMetodoPago().getUltimoscuatrodigitos());
        metodoPago.setFechaVencimiento(req.getPago().getMetodoPago().getFechaVencimiento());
        metodoPago.setActivo(true);
        metodoPago.setUsuario(usuario);
        metodoPago = metodoPagoRepository.save(metodoPago);

        // 6. Registrar el pago
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setTotal(req.getPago().getTotal());
        pago.setIgv(req.getPago().getIgv());
        pago.setEstado_pago(RolEstadoPago.APROBADO);
        pago.setFecha_pago(req.getPago().getFechaPago());
        pago.setMetodoPago(metodoPago);
        pagoRepository.save(pago);

        return reserva;
    }
    
    @Transactional(readOnly = true)
    public List<HistorialReservaResponse> obtenerHistorialPorUsuario(Integer idUsuario) {
        List<Reserva> reservas = reservaRepository.buscarPorUsuario(idUsuario);

        return reservas.stream().map(reserva -> {
            HistorialReservaResponse dto = new HistorialReservaResponse();
            dto.setIdReserva(reserva.getId_reserva());
            dto.setFechaCreacion(reserva.getFechaCreacion());
            dto.setEstado(reserva.getEstado().name());

            List<HabitacionResumen> habitaciones = reservaHabitacionRepository.buscarPorReserva(reserva.getId_reserva())
                    .stream().map(rh -> new HabitacionResumen(
                            rh.getHabitacion().getNombre_habitacion(),
                            rh.getHabitacion().getCategoriaHabitacion().getNombre_categoria(),
                            rh.getHabitacion().getCategoriaHabitacion().getImagen(),
                            rh.getFechaInicio(),
                            rh.getFechaFin(),
                            rh.getPrecioUnitario()
                    )).collect(Collectors.toList());
            dto.setHabitaciones(habitaciones);

            List<ServicioResumen> servicios = reservaServicioRepository.buscarPorReserva(reserva.getId_reserva())
                    .stream().map(rs -> new ServicioResumen(
                            rs.getServicio().getNombre_servicio(),
                            rs.getSubtotal()
                    )).collect(Collectors.toList());
            dto.setServicios(servicios);

            pagoRepository.buscarPorReserva(reserva.getId_reserva()).ifPresent(pago -> {
                PagoResumen pagoResumen = new PagoResumen(
                        pago.getTotal(),
                        pago.getIgv(),
                        pago.getEstado_pago().name(),
                        pago.getFecha_pago(),
                        pago.getMetodoPago() != null ? pago.getMetodoPago().getTipo().name() : null
                );
                dto.setPago(pagoResumen);
            });

            return dto;
        }).collect(Collectors.toList());
    }
}