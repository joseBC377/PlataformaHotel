package com.example.hotel.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name = "SERVICIO")
public class Servicio {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id_servicio;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    @Column(unique=true, nullable= false, length=  50 )
    private String nombre;
        
    @NotBlank(message = "La descripción es obligatoria")
    @Lob
    @Column(columnDefinition ="TEXT"  ,nullable = false)
    private String descripcion;
   
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    @Column(nullable = false)
    private BigDecimal precio;

    @NotBlank(message = "La imagen es obligatoria")
    @Column(nullable = false, length = 255)
    private String imagen;
   
}
