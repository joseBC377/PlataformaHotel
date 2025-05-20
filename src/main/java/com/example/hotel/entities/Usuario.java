package com.example.hotel.entities;




import com.example.hotel.util.Rol;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import java.util.List;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // permite al JPA convertir la clase a base de datos
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Integer id;
    // Column dice a hibernate que mapee el campo al SQL, le permite agregar las
    // funciones especificas de la base datos
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese Nombre")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener numeros")
    @Size(min = 2, max = 50, message = "Ingrese un nombre entre 2 y 50 caracteres")
    private String nombre;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Ingrese apellido")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "No puede contener numeros")
    @Size(min = 2, max = 50, message = "Ingrese un apellido entre 2 y 50 caracteres")
    private String apellido;

    @Column(nullable = false,length = 100, unique = true)
    @NotBlank(message = "Ingrese correo")
    @Email(message = "Formato de correo no valido")
    private String correo;

    @Column(nullable = false,length = 15)
    @NotBlank(message = "Ingrese telefono")
    @Pattern(regexp="\\d{9}", message="El telefono debe tener 9 digitos")
    private String telefono;
    
    @Column(nullable = false)
    @NotBlank(message = "Ingrese contraseña")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
    
    //experimental todavia no a sido probado con roles security
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Ingrese rol")
    private Rol rol;

    //prueba de tablas de contacto
    // @OneToMany(mappedBy = "usuario", cascade =CascadeType.ALL, orphanRemoval = true )
    // @JsonIgnoreProperties("usuario")
    // private List<Contacto> contacto;

}
