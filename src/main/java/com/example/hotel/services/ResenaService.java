package com.example.hotel.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Resena;
import com.example.hotel.repositories.ResenaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResenaService {
    private final ResenaRepository repository;

    public List<Resena>selectAllResenas(){
        return repository.findAll();
    }
    public Optional<Resena> getResenaById(Integer id) {
        return repository.findById(id);
    }

    public Resena insertResena(Resena resena) {
        return repository.save(resena);
    }


    public Optional<Resena> updateResena(Integer id, Resena resena) {
        return repository.findById(id).map(existing -> {
            existing.setCalificacion(resena.getCalificacion());
            existing.setFecha(resena.getFecha());
            existing.setUsuario(resena.getUsuario());
            existing.setHabitacion(resena.getHabitacion());
            return repository.save(existing);
        });
    }

    public boolean deleteResena(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
