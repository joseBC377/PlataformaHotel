package com.example.hotel.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "SERVICIO")
public class Servicio {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id_servicio;

    @Column(unique=true, nullable= false, length=  100 )
    private String nombre;

    @Column(nullable = false, length = 150)
    private String descripcion;
   
    @Column(nullable = false, length = 150)
    private BigDecimal precio;

    @Column(nullable = false, length = 150)
    private String imagen;
   
}
