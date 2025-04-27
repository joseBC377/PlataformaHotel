package com.example.hotel.services;import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.hotel.entities.Habitacion;
import com.example.hotel.repositories.HabitacionRepository;
import lombok.AllArgsConstructor;
//Inyeccion de dependencias
@Service
@AllArgsConstructor
public class HabitacionService {

    private final HabitacionRepository repository;
    
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
