package com.example.hotel.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.ReservaServicio;
import com.example.hotel.repositories.ReservaServicioRepository;

@Service
public class ReservaServicioService {

    //inyeccion de dependencia por contructor
    private final ReservaServicioRepository repository;

    public ReservaServicioService (ReservaServicioRepository repository){
        this.repository = repository;

    }


    public ReservaServicio guardar(ReservaServicio reservaServicio) {
        return repository.save(reservaServicio);
    }

    public List<ReservaServicio> listarTodas() {
        return repository.findAll();
    }


}
