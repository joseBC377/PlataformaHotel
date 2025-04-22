package com.example.hotel.Services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;


import com.example.hotel.entities.Servicio;
import com.example.hotel.repositories.ServicioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServicioService {
    private final ServicioRepository repository;

     public List<Servicio>sellectAllServicios(){
        return repository.findAll();
    }
    public Optional<Servicio> getServicioById(Integer id) {
        return repository.findById(id);
    }

    public Servicio insertServicio(Servicio servicio) {
        return repository.save(servicio);
    }


    public Optional<Servicio> updateServicio(Integer id, Servicio servicio) {
        return repository.findById(id).map(existing -> {
            existing.setNombre(servicio.getNombre());
            existing.setDescripcion(servicio.getDescripcion());
            existing.setImagen(servicio.getImagen());
            existing.setPrecio(servicio.getPrecio());
            return repository.save(existing);
        });
    }

    public boolean deleteServicio(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
