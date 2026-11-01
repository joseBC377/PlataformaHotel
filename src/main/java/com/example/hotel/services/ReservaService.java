package com.example.hotel.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotel.entities.Reserva;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.repositories.ReservaHabitacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservaService {
    
    private final ReservaRepository repository;
    private final ReservaHabitacionRepository reservaHabitacionRepository;
    private final PowerAutomateSyncService powerAutomateSyncService;

    public List<Reserva> selectAllReserva(){
        return repository.findAll();
    }

    public Optional<Reserva> selectById(Integer id) {
        return repository.findById(id); 
    }

    @Transactional
    public Reserva insUpdReserva(Reserva reserva){
        boolean esNueva = (reserva.getId_reserva() == null);
        Reserva guardada = repository.save(reserva);

        // Si es una nueva reserva creada por recepción, sincronizar con Power Automate
        if (esNueva && guardada.getUsuario() != null) {
            try {
                String nombreHuesped = guardada.getUsuario().getNombre_usuario() + " " + guardada.getUsuario().getApellido_paterno();
                String correoCliente = guardada.getUsuario().getCorreo();
                String fechaCreacion = guardada.getFechaCreacion() != null ? guardada.getFechaCreacion().toString() : LocalDate.now().toString();

                // Buscar habitación asociada si existe
                var habitaciones = reservaHabitacionRepository.buscarPorReserva(guardada.getId_reserva());
                String habitacionNombre = "Habitación General";
                String entrada = LocalDate.now().toString();
                String salida = LocalDate.now().plusDays(1).toString();
                long noches = 1;

                if (!habitaciones.isEmpty()) {
                    var rh = habitaciones.get(0);
                    habitacionNombre = rh.getHabitacion().getNombre_habitacion();
                    if (rh.getFechaInicio() != null) entrada = rh.getFechaInicio().toString();
                    if (rh.getFechaFin() != null) salida = rh.getFechaFin().toString();
                    if (rh.getFechaInicio() != null && rh.getFechaFin() != null) {
                        noches = java.time.temporal.ChronoUnit.DAYS.between(rh.getFechaInicio(), rh.getFechaFin());
                        if (noches <= 0) noches = 1;
                    }
                }

                powerAutomateSyncService.sincronizarReserva(
                    guardada.getId_reserva().longValue(),
                    nombreHuesped,
                    correoCliente,
                    fechaCreacion,
                    entrada,
                    salida,
                    noches,
                    habitacionNombre,
                    guardada.getEstado() != null ? guardada.getEstado().name() : "PENDIENTE"
                );
            } catch (Exception e) {
                System.err.println("Error al sincronizar reserva de recepción: " + e.getMessage());
            }
        }

        return guardada;
    }

    @Transactional
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    } 
}