package com.example.hotel.services;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Reserva;
import com.example.hotel.repositories.ReservaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservaService {
    private final ReservaRepository repository;

    // select*from 
    public List<Reserva> selectAllReserva(){
        return repository.findAll();
    }
    
    // Seleccionar reserva por ID
    public Optional<Reserva> selectById(Integer id) {
        return repository.findById(id); 
    }

    // insert - update
    public Reserva insUpdReserva(Reserva reserva){
        return repository.save(reserva);
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
