package com.example.hotel.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.ReservaHabitacion;
import com.example.hotel.repositories.ReservaHabitacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservaHabitacionService {

    private final ReservaHabitacionRepository repository;

    // ==========================================================
    // LISTAR
    // ==========================================================

    public List<ReservaHabitacion> selectAllReserva() {

        return repository.findAll();
    }

    // ==========================================================
    // BUSCAR POR ID
    // ==========================================================

    public Optional<ReservaHabitacion> selectById(
            Integer id) {

        return repository.findById(id);
    }

    // ==========================================================
    // INSERTAR / ACTUALIZAR
    // ==========================================================

    public ReservaHabitacion insUpdReservaHabit(
            ReservaHabitacion reservaHabitacion) {

        if (reservaHabitacion.getFechaInicio() == null
                || reservaHabitacion.getFechaFin() == null) {

            throw new RuntimeException(
                    "Las fechas de la reserva son obligatorias."
            );
        }

        if (!reservaHabitacion
                .getFechaFin()
                .isAfter(
                        reservaHabitacion.getFechaInicio()
                )) {

            throw new RuntimeException(
                    "La fecha fin debe ser posterior a la fecha inicio."
            );
        }

        return repository.save(
                reservaHabitacion
        );
    }

    // ==========================================================
    // COMPROBAR DISPONIBILIDAD
    // ==========================================================

    public boolean habitacionDisponible(
            Integer idHabitacion,
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        if (idHabitacion == null) {

            throw new RuntimeException(
                    "El ID de la habitación es obligatorio."
            );
        }

        if (fechaInicio == null || fechaFin == null) {

            throw new RuntimeException(
                    "Las fechas son obligatorias para consultar disponibilidad."
            );
        }

        if (!fechaFin.isAfter(fechaInicio)) {

            throw new RuntimeException(
                    "La fecha de salida debe ser posterior a la fecha de ingreso."
            );
        }

        long reservas =
                repository.contarReservasSolapadas(
                        idHabitacion,
                        fechaInicio,
                        fechaFin
                );

        return reservas == 0;
    }

    // ==========================================================
    // ELIMINAR
    // ==========================================================

    public boolean delete(
            Integer id) {

        if (repository.existsById(id)) {

            repository.deleteById(id);

            return true;
        }

        return false;
    }
}