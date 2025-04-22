package com.example.hotel.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Contacto;
import com.example.hotel.repositories.ContactoRepository;

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
    public Contacto insert(Contacto contacto){
        return repository.save(contacto);
    }
    public Contacto updateContact(Integer id,Contacto contacto){
        if (!repository.existsById(contacto.getId())) {
            throw new RuntimeException("No se encontro el id "+contacto.getId());
        }
        return repository.save(contacto);
    }
    public void deleteContact(Integer id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("No se encuentra el id :" +id);
        }
        repository.deleteById(id);
        System.out.println("Uusuario eliminado "+id);
    }

}
