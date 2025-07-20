package com.example.hotel.entities;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVA")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // También ajusta DateTimeFormat
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") // Este es el formato que envía el datetime-local de HTML
    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    private LocalDateTime fecha_inicio;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // También ajusta DateTimeFormat
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") // Este es el formato que envía el datetime-local de HTML
    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    private LocalDateTime fecha_fin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = true)
    @JsonIgnoreProperties("reserva")
    private Usuario usuario;

}
