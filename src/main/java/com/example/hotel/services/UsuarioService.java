package com.example.hotel.services;
import java.util.List;


import org.springframework.stereotype.Service;

import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> selectAll() {
        return repository.findAll();
    }

    public Usuario selectId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el id: " + id));
    }

    public Usuario insertUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario updateUsuario(Integer id, Usuario usuario) {
        if (!repository.existsById(usuario.getId())) {
            throw new RuntimeException("No se puede actualizar el usuario no existe");
        }
        return repository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El usuario "+id+ " no exite");
        }
        repository.deleteById(id);
    }
}
