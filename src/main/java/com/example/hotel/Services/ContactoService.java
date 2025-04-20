package com.example.hotel.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Contacto;
import com.example.hotel.repository.ContactoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactoService {

    private final ContactoRepository repository;

    public List<Contacto> selectAll() {
        return repository.findAll();
    }

    public Contacto selectId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el id : "+id));
    }
    public Contacto insertId(Contacto contacto){
        return repository.save(contacto);
    }
    public Contacto updateId(Contacto contacto){
        if (!repository.existsById(contacto.getId())) {
            throw new RuntimeException("No se encontro el id "+contacto.getId());
        }
        return repository.save(contacto);
    }
    public void deleteId(Integer id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se encuentra el id :" +id);
        }
        repository.deleteById(id);
        System.out.println("Uusuario eliminado "+id);
    }

}
