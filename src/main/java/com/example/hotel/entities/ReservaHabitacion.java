package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
//import java.time.LocalDateTime;

//import org.springframework.format.annotation.DateTimeFormat;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @FutureOrPresent(message = "La fecha de inicio debe ser actual o futura")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @FutureOrPresent(message = "La fecha fin debe ser actual o futura")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio debe ser positivo")
    @Column(name = "precio_uni", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @NotNull(message = "La reserva es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnoreProperties("reservaHabitacion") 
    private Reserva reserva;

    @NotNull(message = "La habitación es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitacion", nullable = false)
    @JsonIgnoreProperties({"categoriaHabitacion"}) 
    private Habitacion habitacion;
}