package com.example.hotel.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_servicio;
    
    @NotNull
    @NotBlank(message="Ingrese un nombre")
    @Pattern(regexp = "\\D*",message="No puede contener numeros")
    private String Nombre;

    @NotNull
    @NotBlank(message="Ingrese una descripcion")
    private String Descripcion;

    @NotNull
    private BigDecimal Precio;


    @NotNull
    @NotBlank(message="Ingrese una imagen")
    private String Imagen;
    
}
