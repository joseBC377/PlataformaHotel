package com.example.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Integer>{
    List<Reserva> findByUsuarioId(Integer UsuarioID);
}
