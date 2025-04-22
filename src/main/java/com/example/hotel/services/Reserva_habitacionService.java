package com.example.hotel.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Reserva_habitacion;
import com.example.hotel.repositories.Reserva_habitacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Reserva_habitacionService {
     private final Reserva_habitacionRepository repository;

    // select*from 
    public List<Reserva_habitacion> selectAllReserva(){
        return repository.findAll();
    }
    
    // Seleccionar reserva_habitacion por ID
    public Optional<Reserva_habitacion> selectById(Integer id) {
        return repository.findById(id); 
    }

    // insert - update
    public Reserva_habitacion insUpdReservaHabit(Reserva_habitacion reserva_habitacion){
        return repository.save(reserva_habitacion);
    }
    // DELETE
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }    
}
