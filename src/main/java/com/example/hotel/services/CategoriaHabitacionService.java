package com.example.hotel.services;import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.hotel.entities.CategoriaHabitacion;
import com.example.hotel.repositories.CategoriaHabitacionRepository;
import lombok.AllArgsConstructor;

//Inyeccion de dependencias
@Service
@AllArgsConstructor
public class CategoriaHabitacionService {
    private final CategoriaHabitacionRepository repository;
    

    public List<CategoriaHabitacion>selCategoriaHabitacions(){
        return repository.findAll();
    }

    public Optional<CategoriaHabitacion> getCategoriaById(Integer id) {
        return repository.findById(id);
    }

    public CategoriaHabitacion insertCategoria(CategoriaHabitacion categoria) {
        return repository.save(categoria);
    }

    public Optional<CategoriaHabitacion> updateCategoria(Integer id, CategoriaHabitacion categoria) {
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
