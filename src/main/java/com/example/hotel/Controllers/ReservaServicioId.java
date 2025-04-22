package com.example.hotel.controllers;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReservaServicioId implements Serializable {

    private Integer reserva;
    private Integer servicio;


    public ReservaServicioId() {

    }

    public ReservaServicioId(Integer reserva, Integer servicio) {
        this.reserva = reserva;
        this.servicio = servicio;
    }


    
    public Integer getReserva() {
        return reserva;
    }


    public void setReserva(Integer reserva) {
        this.reserva = reserva;
    }


    public Integer getServicio() {
        return servicio;
    }


    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaServicioId)) return false;
        ReservaServicioId that = (ReservaServicioId) o;
        return Objects.equals(reserva, that.reserva) &&
               Objects.equals(servicio, that.servicio);
    }


    @Override
    public int hashCode() {
        return Objects.hash(reserva, servicio);
    }

    

    

}
