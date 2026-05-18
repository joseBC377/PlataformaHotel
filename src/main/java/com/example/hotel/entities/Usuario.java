package com.example.hotel.entities;

import java.time.LocalDate;
import java.util.List;

import com.example.hotel.util.Rol;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // permite al JPA convertir la clase a base de datos
@Table(name = "USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "id_usuario")
    private Integer id_usuario;
    // Column dice a hibernate que mapee el campo al SQL, le permite agregar las
    // funciones especificas de la base datos
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese Nombre")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener numeros")
    @Size(min = 2, max = 50, message = "Ingrese un nombre entre 2 y 50 caracteres")
    private String nombre_usuario;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese apellido")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener numeros")
    @Size(min = 2, max = 50, message = "Ingrese un apellido entre 2 y 50 caracteres")
    private String apellido_paterno;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese apellido")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener numeros")
    @Size(min = 2, max = 50, message = "Ingrese un apellido entre 2 y 50 caracteres")
    private String apellido_materno;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Ingrese correo")
    @Email(message = "Formato de correo no valido")
    private String correo;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Ingrese telefono")
    @Pattern(regexp = "\\d{9}", message = "El telefono debe tener 9 digitos")
    private String telefono;

    @Column(nullable = false)
    @NotBlank(message = "Ingrese contraseña")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fecha_nacimiento;

    // experimental todavia no a sido probado con roles security
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Ingrese rol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
    private List<Reserva> reserva;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
    private List<Resena> resena;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("usuario")
    private List<MetodoPago> metodoPago;

}


