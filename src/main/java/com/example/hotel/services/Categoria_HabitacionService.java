package com.example.hotel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.hotel.entities.Categoria_Habitacion;
import com.example.hotel.repositories.Categoria_HabitacionRepository;


import lombok.AllArgsConstructor;

//Inyeccion de dependencias
@Service
@AllArgsConstructor
public class Categoria_HabitacionService {
    private final Categoria_HabitacionRepository repository;
    

    public List<Categoria_Habitacion>selCategoria_Habitacions(){
        return repository.findAll();
    }

    public Optional<Categoria_Habitacion> getCategoriaById(Integer id) {
        return repository.findById(id);
    }

    public Categoria_Habitacion insertCategoria(Categoria_Habitacion categoria) {
        return repository.save(categoria);
    }

    public Optional<Categoria_Habitacion> updateCategoria(Integer id, Categoria_Habitacion categoria) {
        return repository.findById(id).map(existing -> {
            existing.setNombre(categoria.getNombre());
            existing.setDescripcion(categoria.getDescripcion());
            existing.setCapacidad(categoria.getCapacidad());
            existing.setPrecio(categoria.getPrecio());
            existing.setImagen(categoria.getImagen());
            return repository.save(existing);
        });
    }

    public boolean deleteCategoria(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
