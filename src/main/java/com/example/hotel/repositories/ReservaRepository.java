package com.example.hotel.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario")
    List<Reserva> findAll();

    @Query("SELECT r FROM Reserva r JOIN FETCH r.usuario WHERE r.id_reserva = :id")
    Optional<Reserva> findById(@Param("id") Integer id);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id_usuario = :idUsuario")
    List<Reserva> buscarPorUsuario(@Param("idUsuario") Integer idUsuario);
}
