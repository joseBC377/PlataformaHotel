package com.example.hotel.entities;

import com.example.hotel.controllers.ReservaServicioId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva_Servicio {


    @EmbeddedId
    private ReservaServicioId id;

    @ManyToOne
    @MapsId("reserva") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @MapsId("servicio") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;


}
