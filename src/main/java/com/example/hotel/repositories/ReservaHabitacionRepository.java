package com.example.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.ReservaHabitacion;

public interface ReservaHabitacionRepository extends JpaRepository<ReservaHabitacion,Integer>{
    List<ReservaHabitacion> findByReservaId(Integer ReservaID);
    // activar cuando habitacion exista
    // List<Reserva_habitacion> findByHabitacionId(Integer HabitacionID);
}