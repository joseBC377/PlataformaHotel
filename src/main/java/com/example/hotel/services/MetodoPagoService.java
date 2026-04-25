package com.example.hotel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.MetodoPago;
import com.example.hotel.repositories.MetodoPagoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository repository;

    public List<MetodoPago> listar() {
        return repository.findAll();
    }

    public Optional<MetodoPago> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    public MetodoPago guardar(MetodoPago metodoPago) {
        return repository.save(metodoPago);
    }

    public Optional<MetodoPago> actualizar(Integer id, MetodoPago metodoPago) {
        return repository.findById(id).map(existing -> {
            existing.setTipo(metodoPago.getTipo());
            existing.setUltimoscuatrodigitos(metodoPago.getUltimoscuatrodigitos());
            existing.setFechaVencimiento(metodoPago.getFechaVencimiento());
            existing.setToken(metodoPago.getToken());
            existing.setActivo(metodoPago.getActivo());
            existing.setUsuario(metodoPago.getUsuario());
            return repository.save(existing);
        });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}