package com.example.hotel.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Integer>{
    List<Reserva> findByUsuarioId(Integer UsuarioID);

}
