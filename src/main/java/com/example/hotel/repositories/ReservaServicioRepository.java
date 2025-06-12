package com.example.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.ReservaServicio;
import com.example.hotel.entities.ReservaServicioId;

public interface ReservaServicioRepository extends JpaRepository <ReservaServicio, ReservaServicioId> {

}
