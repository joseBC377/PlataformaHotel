package com.example.hotel.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaServicio { 

    //Esto le dice a JPA que la clave primaria de Reserva_Servicio está compuesta y que se define 
    //en ReservaServicioId.
    @EmbeddedId
    private ReservaServicioId id;

    @NotNull(message = "La reserva es obligatoria")
    @ManyToOne
    @MapsId("reserva") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id_reserva", nullable = false) //El id tiene que ser el mismo con la otra tabla porque puede aber confusion
    private Reserva reserva;

    @NotNull(message = "El servicio es obligatorio")
    @ManyToOne
    @MapsId("servicio") // el nombre del campo en ReservaServicioId
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    

}
