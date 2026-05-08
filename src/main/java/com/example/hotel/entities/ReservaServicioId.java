package com.example.hotel.entities;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable //Indica que esta no es una entidad independiente sino que se incrsuta en otra identidad
//en este caso dentro de Reserva_Servicio como su clave PK, esto lo que le dice es:
//Oye la clave primaria de esta entidad es una combinacion de estos 2 campos
public class ReservaServicioId implements Serializable {

    private Integer idReserva;
    private Integer idServicio;

    public ReservaServicioId() {}

    public ReservaServicioId(Integer idReserva, Integer idServicio) {
        this.idReserva = idReserva;
        this.idServicio = idServicio;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaServicioId)) return false;
        ReservaServicioId that = (ReservaServicioId) o;
        return Objects.equals(idReserva, that.idReserva) &&
               Objects.equals(idServicio, that.idServicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva, idServicio);
    }
}