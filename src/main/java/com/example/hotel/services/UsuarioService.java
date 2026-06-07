package com.example.hotel.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.util.ConteoRol;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> selectAllClient() {
        return repository.TodosClient();
    }

    public List<Usuario> TodosUsuarioReserva() {
        return repository.TodosUsuarioReserva();
    }

    public List<ConteoRol> contarUsuariosRol() {
        return repository.contarUsuariosRol();
    }

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
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar el usuario no existe"));

        existente.setNombre_usuario(usuario.getNombre_usuario());
        existente.setApellido_paterno(usuario.getApellido_paterno());
        existente.setApellido_materno(usuario.getApellido_materno());
        existente.setCorreo(usuario.getCorreo());
        existente.setTelefono(usuario.getTelefono());
        existente.setFecha_nacimiento(usuario.getFecha_nacimiento());
        existente.setRol(usuario.getRol());

        // Solo actualizar la contraseña si se envía una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            existente.setPassword(usuario.getPassword());
        }

        return repository.save(existente);
    }

    public void deleteUsuario(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El usuario " + id + " no exite");
        }
        repository.deleteById(id);
    }

    // PARA EL SPRING SECURITY (Seguridad)
    public Usuario findByCorreo(String correo) {
        return repository.findByCorreo(correo).orElseThrow();
    }

}
