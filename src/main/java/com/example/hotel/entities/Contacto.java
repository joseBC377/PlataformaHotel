package com.example.hotel.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTACTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer id_contacto;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese nombre")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener números")
    @Size(min = 2, max = 50, message = "Ingrese mínimo 2 caracteres, máximo 50")
    private String nombre;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese Apellido")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message="No puede contener números")
    @Size(min = 2, max = 50, message = "Ingrese mínimo 2 caracteres, máximo 50")
    private String apellido;

    @Column(nullable = false, length = 100) // Aumentado para correos largos
    @Email(message = "Formato de correo inválido")
    @NotBlank(message = "Ingrese correo")
    private String correo;

    @Column(nullable = false, columnDefinition = "TEXT") // TEXT permite mensajes largos sin límite de 20
    @NotBlank(message = "Ingrese mensaje")
    private String mensaje;
}