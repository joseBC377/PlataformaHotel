package com.example.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.Reserva_habitacion;

public interface Reserva_habitacionRepository extends JpaRepository<Reserva_habitacion,Integer>{
    List<Reserva_habitacion> findByReservaId(Integer ReservaID);
    // activar cuando habitacion exista
    // List<Reserva_habitacion> findByHabitacionId(Integer HabitacionID);
}