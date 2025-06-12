package com.example.hotel.entities;


// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.FetchType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Setter
@Getter 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HABITACION")
public class Habitacion {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(unique=true, nullable= false, length=  50 )
    private String nombre;

    @Lob
    @Column(columnDefinition = "TEXT",nullable = false)
    private String descripcion;
    
    @Column(nullable = false, length = 20)
    private String estado;

    //Añadir llave foranea 
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private CategoriaHabitacion categoriaHabitacion;
}
