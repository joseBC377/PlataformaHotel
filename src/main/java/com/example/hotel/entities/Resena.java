package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Setter
@Getter 
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESENA")
public class Resena {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(nullable = false, length = 150)
    @NotNull(message = "La calificación es obligatoria")
    private BigDecimal calificacion;

    @Column(nullable = false, length = 150)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El usuario es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;

    @NotNull(message = "La habitación es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;

}
