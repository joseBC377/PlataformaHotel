package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVA_HABITACION")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservaHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    
    @Column(nullable = false)
    private LocalDate fecha_inicio;

    

    @Column(nullable = false)
    private LocalDate fecha_fin;


    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio debe ser un valor positivo")
    @Column(name = "precio_uni", precision = 10, scale = 2)
    private BigDecimal precio_uni;

    @NotNull(message = "La reserva es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = true)
    private Reserva reserva;

    @NotNull(message = "La habitacion es obligatoria")
    @ManyToOne
    @JoinColumn(name = "id_habitacion", nullable = true)
    private Habitacion habitacion;

}
