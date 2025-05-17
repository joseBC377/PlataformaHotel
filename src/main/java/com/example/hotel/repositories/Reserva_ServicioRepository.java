package com.example.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.ReservaServicioId;
import com.example.hotel.entities.Reserva_Servicio;

public interface Reserva_ServicioRepository extends JpaRepository <Reserva_Servicio, ReservaServicioId> {

}
