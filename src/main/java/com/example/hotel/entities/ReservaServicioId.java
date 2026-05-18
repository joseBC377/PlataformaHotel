package com.example.hotel.entities;
import java.io.Serializable;
//import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaServicioId implements Serializable {
    @Column(name = "id_reserva")
    private Integer idReserva;

    @Column(name = "id_servicio")
    private Integer idServicio;
}