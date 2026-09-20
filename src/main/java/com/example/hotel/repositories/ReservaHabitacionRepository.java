package com.example.hotel.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.ReservaHabitacion;

public interface ReservaHabitacionRepository
        extends JpaRepository<ReservaHabitacion, Integer> {

    // Buscar habitaciones asociadas a una reserva
    @Query("""
        SELECT rh
        FROM ReservaHabitacion rh
        WHERE rh.reserva.id_reserva = :idReserva
    """)
    List<ReservaHabitacion> buscarPorReserva(
            @Param("idReserva") Integer idReserva
    );

    // Verificar si una habitación tiene una reserva
    // que se cruce con el rango solicitado.
    //
    // Si una reserva termina exactamente el día en que
    // comienza la siguiente, permitimos la nueva reserva.
    @Query("""
        SELECT COUNT(rh)
        FROM ReservaHabitacion rh
        WHERE rh.habitacion.id_habitacion = :idHabitacion
        AND rh.reserva.estado <> com.example.hotel.util.RolReserva.CANCELADO
        AND rh.fechaInicio < :fechaFin
        AND rh.fechaFin > :fechaInicio
    """)
    long contarReservasSolapadas(
            @Param("idHabitacion") Integer idHabitacion,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}