package com.example.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entities.Usuario;
/*El jpa repository permite metodos como findAll() lista todos los usuarios,findByid(),save() guarada o actualiza,deleteByid(), en los operadores diamantes va la clase d la tabla y su primary key el jpa repository evita escribir codigo sql*/
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
