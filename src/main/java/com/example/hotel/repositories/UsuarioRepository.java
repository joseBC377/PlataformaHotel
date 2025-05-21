package com.example.hotel.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotel.entities.Usuario;
import com.example.hotel.util.ConteoRol;


/*El jpa repository permite metodos como findAll() lista todos los usuarios,findByid(),save() guarada o actualiza,deleteByid(), en los operadores diamantes va la clase d la tabla y su primary key el jpa repository evita escribir codigo sql*/
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    Optional <Usuario> findByCorreo(String correo);
    //Querys nativos
    @Query(value = "SELECT * FROM usuario WHERE rol = 'CLIENT';", nativeQuery = true)
    List<Usuario>TodosClient();

    @Query(value = "SELECT DISTINCT u.* FROM usuario u JOIN reserva r ON r.id_usuario = u.id;", nativeQuery = true)
    List<Usuario>TodosUsuarioReserva();
    
    @Query(value = "SELECT rol, COUNT(*) FROM usuario GROUP BY rol;", nativeQuery = true)
    List<ConteoRol> contarUsuariosRol();



}
