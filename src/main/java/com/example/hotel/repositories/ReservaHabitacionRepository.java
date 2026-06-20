package com.example.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.ReservaHabitacion;

public interface ReservaHabitacionRepository extends JpaRepository<ReservaHabitacion,Integer>{
 //List<ReservaHabitacion> findByReservaId(Integer reservaId);    // activar cuando habitacion exista
    // List<Reserva_habitacion> findByHabitacionId(Integer HabitacionID);

    @Query("SELECT rh FROM ReservaHabitacion rh WHERE rh.reserva.id_reserva = :idReserva")
    List<ReservaHabitacion> buscarPorReserva(@Param("idReserva") Integer idReserva);
}