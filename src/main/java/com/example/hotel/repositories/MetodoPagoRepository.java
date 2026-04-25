package com.example.hotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.MetodoPago;
import com.example.hotel.util.RolMetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    Optional<MetodoPago> findByTipo(RolMetodoPago tipo);

}