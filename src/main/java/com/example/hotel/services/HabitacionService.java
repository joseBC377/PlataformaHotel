package com.example.hotel.services;import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.repositories.CategoriaHabitacionRepository;
import com.example.hotel.repositories.HabitacionRepository;
import lombok.AllArgsConstructor;
//Inyeccion de dependencias
@Service
@AllArgsConstructor
public class HabitacionService {

    private final HabitacionRepository repository;
    private final CategoriaHabitacionRepository categoriarepository;
    
    public List<Habitacion>selectAllHabitacions(){
        return repository.findAll();
    }
    public Optional<Habitacion> getHabitacionById(Integer id) {
        return repository.findById(id);
    }

    public Habitacion insertHabitacion(Habitacion habitacion) {
        return repository.save(habitacion);
    }

    public Optional<Habitacion> updateHabitacion(Integer id, Habitacion habitacion) {
        return repository.findById(id).map(existing -> {
            existing.setNombre(habitacion.getNombre());
            existing.setDescripcion(habitacion.getDescripcion());
            existing.setEstado(habitacion.getEstado());
            // Verificamos si la categoría de habitación está presente y actualizamos
            if (habitacion.getCategoriaHabitacion() != null && habitacion.getCategoriaHabitacion().getId() != null) {
                CategoriaHabitacion nuevaCategoria = categoriarepository
                        .findById(habitacion.getCategoriaHabitacion().getId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                existing.setCategoriaHabitacion(nuevaCategoria);
            }
            return repository.save(existing);
        });
    }

    public boolean deleteHabitacion(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
