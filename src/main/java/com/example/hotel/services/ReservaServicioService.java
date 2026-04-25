package com.example.hotel.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.*;
import com.example.hotel.repositories.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservaServicioService {

    private final ReservaServicioRepository reservaServicioRepository;
    private final ReservaRepository reservaRepository;
    private final ServicioRepository servicioRepository;

    public List<ReservaServicio> listarTodas() {
        return reservaServicioRepository.findAll();
    }

    public ReservaServicio crearRelacion(ReservaServicio reservaServicio) {

        Reserva reserva = reservaRepository.findById(reservaServicio.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Servicio servicio = servicioRepository.findById(reservaServicio.getServicio().getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        ReservaServicioId id = new ReservaServicioId(reserva.getId(), servicio.getIdServicio());

        reservaServicio.setId(id);
        reservaServicio.setReserva(reserva);
        reservaServicio.setServicio(servicio);

        return reservaServicioRepository.save(reservaServicio);
    }

    public ReservaServicio actualizarRelacion(Integer idReserva, Integer idServicio, ReservaServicio nuevaRelacion) {

        ReservaServicioId idActual = new ReservaServicioId(idReserva, idServicio);

        if (!reservaServicioRepository.existsById(idActual)) {
            throw new RuntimeException("Relación no encontrada");
        }

        Reserva nuevaReserva = reservaRepository.findById(nuevaRelacion.getReserva().getId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Servicio nuevoServicio = servicioRepository.findById(nuevaRelacion.getServicio().getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        reservaServicioRepository.deleteById(idActual);

        ReservaServicioId nuevoId = new ReservaServicioId(nuevaReserva.getId(), nuevoServicio.getIdServicio());

        nuevaRelacion.setId(nuevoId);
        nuevaRelacion.setReserva(nuevaReserva);
        nuevaRelacion.setServicio(nuevoServicio);

        return reservaServicioRepository.save(nuevaRelacion);
    }

    public void eliminarRelacion(Integer idReserva, Integer idServicio) {
        ReservaServicioId id = new ReservaServicioId(idReserva, idServicio);

        if (!reservaServicioRepository.existsById(id)) {
            throw new RuntimeException("Relación no encontrada");
        }

        reservaServicioRepository.deleteById(id);
    }
}