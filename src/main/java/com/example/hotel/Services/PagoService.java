package com.example.hotel.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Pago;
import com.example.hotel.repositories.PagoRepository;

@Service
public class PagoService {

    //inyeccion de dependencia por contructor
    private final PagoRepository repository;

    public PagoService (PagoRepository repository){
        this.repository = repository;

    }

    
    public List <Pago>listarTodas(){
        return repository.findAll();
    }

    public Pago insert(Pago pago){
        return repository.save(pago);
    }
    

    public Pago obtenerPorId (Integer idPago){
        return repository.findById(idPago).orElse(null);
    }

    public Pago actualizarPago(Integer idPago,Pago pago){
        if (!repository.existsById(pago.getIdPago())) {
            throw new RuntimeException("No se encontro el id "+pago.getIdPago());
        }
        return repository.save(pago);
    }

    public void eliminar (Integer id_pago){
        repository.deleteById(id_pago);
    }


}
