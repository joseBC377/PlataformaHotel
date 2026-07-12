package com.example.hotel.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    @Query("SELECT p FROM Pago p WHERE p.reserva.id_reserva = :idReserva")
    Optional<Pago> buscarPorReserva(@Param("idReserva") Integer idReserva);

    // para el dashboard
    @Query("SELECT p FROM Pago p WHERE p.fecha_pago BETWEEN :inicio AND :fin")
    List<Pago> buscarPorRangoFechas(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}
