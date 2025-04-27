package com.example.hotel.services;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Reserva_Servicio;
import com.example.hotel.repositories.Reserva_ServicioRepository;

@Service
public class Reserva_ServicioService {

    //inyeccion de dependencia por contructor
    private final Reserva_ServicioRepository repository;

    public Reserva_ServicioService (Reserva_ServicioRepository repository){
        this.repository = repository;

    }


    public Reserva_Servicio guardar(Reserva_Servicio reservaServicio) {
        return repository.save(reservaServicio);
    }

    public List<Reserva_Servicio> listarTodas() {
        return repository.findAll();
    }


}
