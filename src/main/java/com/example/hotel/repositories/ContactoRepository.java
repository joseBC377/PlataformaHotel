package com.example.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto,Integer> {
}
