package com.example.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Integer>{
    //List<Reserva> findByUsuarioId(Integer idUsuario);
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id_usuario = :idUsuario")
    List<Reserva> buscarPorUsuario(@Param("idUsuario") Integer idUsuario);


}
