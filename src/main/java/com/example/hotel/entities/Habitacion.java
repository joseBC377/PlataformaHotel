package com.example.hotel.entities;


//import java.util.List;

import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.FetchType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HABITACION")
public class Habitacion {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    @Lob
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Ingrese el estado de la habitación")
    private RolHabitacion estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Ingrese la condición de la habitación")
    private RolTipo tipo;


    //Añadir llave foranea 
    @ManyToOne
    @JoinColumn(name="id_categoria" , nullable = false)
    @JsonIgnoreProperties({"habitacion"})
    private CategoriaHabitacion categoriaHabitacion;



}
