package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "id_resena")
    private Integer id_resena;

    @Column(nullable = false, precision = 2, scale = 1)
    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    @NotNull(message = "La calificación es obligatoria")
    private BigDecimal calificacion;


    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;


    @Lob
    @NotBlank(message = "El comentario no puede estar vacía")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comentario;
    
    @NotNull(message = "El usuario es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"reserva", "contacto", "resena", "metodoPago"})
    private Usuario usuario;

    @NotNull(message = "La habitación es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    @JsonIgnoreProperties({"resena"})
    private Habitacion habitacion;

    @NotNull(message = "El servicio es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    @JsonIgnoreProperties({"resena"})
    private Servicio servicio;

}
