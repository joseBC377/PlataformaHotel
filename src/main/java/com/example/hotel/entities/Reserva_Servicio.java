package com.example.hotel.entities;

import com.example.hotel.controllers.ReservaServicioId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

    //Esto le dice a JPA que la clave primaria de Reserva_Servicio está compuesta y que se define 
    //en ReservaServicioId.
    @EmbeddedId
    private ReservaServicioId id;

    @ManyToOne
    @MapsId("reserva") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id", nullable = false) //El id tiene que ser el mismo con la otra tabla porque puede aber confusion
    private Reserva reserva;

    @ManyToOne
    @MapsId("servicio") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    

}
