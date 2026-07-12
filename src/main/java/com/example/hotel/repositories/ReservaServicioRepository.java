package com.example.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.ReservaServicio;
import com.example.hotel.entities.ReservaServicioId;

public interface ReservaServicioRepository extends JpaRepository<ReservaServicio, ReservaServicioId> {
    @Query("SELECT rs FROM ReservaServicio rs WHERE rs.reserva.id_reserva = :idReserva")
    List<ReservaServicio> buscarPorReserva(@Param("idReserva") Integer idReserva);
}
