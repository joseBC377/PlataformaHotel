package com.example.hotel.entities;

import java.math.BigDecimal;

// import java.util.List;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "CATEGORIA_HABITACION")
public class CategoriaHabitacion {
  @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(unique=true, nullable= false, length=  50 )
    private String nombre;
    @Lob
    @Column(columnDefinition ="TEXT",nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private Integer capacidad;
   
    private BigDecimal precio;

    @Column(nullable = false, length = 255)
    private String imagen;

    //Añadir carga de llave foranea de habitacion
    // @OneToMany(mappedBy = "categoria_Habitacion", cascade = CascadeType.ALL,orphanRemoval = true)
    // @JsonIgnoreProperties("categoria_Habitacion")
    // private List<Habitacion> habitacion;

   
}
