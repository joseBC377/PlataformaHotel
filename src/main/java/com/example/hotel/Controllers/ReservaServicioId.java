package com.example.hotel.controllers;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable //Indica que esta no es una entidad independiente sino que se incrsuta en otra identidad
//en este caso dentro de Reserva_Servicio como su clave PK, esto lo que le dice es:
//Oye la clave primaria de esta entidad es una combinacion de estos 2 campos
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

    
    //para comparar si dos claves son iguales.
    //Si dos objetos son iguales según equals(), deben tener el mismo código hash, en pocas palabras si 2 filas son iguales , si son iguales te mandara un error que no se puede, para no crear registros duplicados.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaServicioId)) return false;
        ReservaServicioId that = (ReservaServicioId) o;
        return Objects.equals(reserva, that.reserva) &&
               Objects.equals(servicio, that.servicio);
    }


    
    //El valor del código hash no debe cambiar mientras el objeto esté en la colección.
    //Segun hashCode , sirve para que los objetos puedan guardarse correctamente en estructuras como HashSet o HashMap
    //Siempre que sobreescribas equals(), también debes sobreescribir hashCode(), o podrías tener errores muy difíciles de detectar
    @Override
    public int hashCode() {
        return Objects.hash(reserva, servicio);
    }

    

    

}
