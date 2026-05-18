package com.example.hotel.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVA_SERVICIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaServicio {

    @EmbeddedId
    private ReservaServicioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idReserva")
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnoreProperties("reservaServicio")
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idServicio")
    @JoinColumn(name = "id_servicio", nullable = false)
    @JsonIgnoreProperties("reservaServicio")
    private Servicio servicio;

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}