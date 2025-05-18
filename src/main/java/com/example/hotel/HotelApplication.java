package com.example.hotel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.hotel.entities.Rol;
import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}


	//Creamos un usuario para hacer pruebas 
	@Bean
	CommandLineRunner commandLineRunner (UsuarioRepository repository, PasswordEncoder encoder){
		return args -> {
			System.out.println("Creando usuario");
			if (repository.findByCorreo("durand@gmail.com").isEmpty()) {
				Usuario usuario = new Usuario();
				usuario.setTelefono("123456789");
				usuario.setApellido("Durand");
				usuario.setNombre("Abel");
				usuario.setCorreo("durand@gmail.com");
				usuario.setPassword(encoder.encode("123456"));
				usuario.setRol(Rol.ADMIN);
				repository.save(usuario);
			}
		};
	}

}
