package com.example.hotel.services;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.ReservaHabitacion;
import com.example.hotel.repositories.ReservaHabitacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservaHabitacionService {
     private final ReservaHabitacionRepository repository;

    // select*from 
    public List<ReservaHabitacion> selectAllReserva(){
        return repository.findAll();
    }
    
    // Seleccionar ReservaHabitacion por ID
    public Optional<ReservaHabitacion> selectById(Integer id) {
        return repository.findById(id); 
    }

    // insert - update
    public ReservaHabitacion insUpdReservaHabit(ReservaHabitacion reservaHabitacion){
        return repository.save(reservaHabitacion);
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
