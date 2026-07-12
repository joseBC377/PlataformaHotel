package com.example.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    
    long countByEstado(RolHabitacion estado);
    long countByTipo(RolTipo tipo);
}
