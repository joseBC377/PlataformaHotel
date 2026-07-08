package com.example.hotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    @Query("SELECT p FROM Pago p WHERE p.reserva.id_reserva = :idReserva")
    Optional<Pago> buscarPorReserva(@Param("idReserva") Integer idReserva);
}
