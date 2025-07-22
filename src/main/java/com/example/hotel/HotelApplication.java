package com.example.hotel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.util.Rol;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	//Creamos un usuario para hacer pruebas 
	@Bean
	CommandLineRunner commandLineRunner (UsuarioRepository usuarioRepository, PasswordEncoder encoder){
		return args -> {
			System.out.println("Creando usuario");
			if (usuarioRepository.findByCorreo("admin@gmail.com").isEmpty()) { //Ponemos la condicional para ver si exite  no lo crea , pero si no exite lo crea en la databse
				Usuario usuario = new Usuario();
				usuario.setTelefono("123456789");
				usuario.setApellido("UserAdmin");
				usuario.setNombre("Admin");
				usuario.setCorreo("admin@gmail.com");
				usuario.setPassword(encoder.encode("12345678"));
				usuario.setRol(Rol.ADMIN);
				usuarioRepository.save(usuario);
			}
		};
	}

}
