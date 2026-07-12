package com.example.hotel.DTOS;

import java.time.LocalDate;
import java.util.List;

public class HistorialReservaResponse {
    private Integer idReserva;
    private LocalDate fechaCreacion;
    private String estado;
    private List<HabitacionResumen> habitaciones;
    private List<ServicioResumen> servicios;
    private PagoResumen pago;

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<HabitacionResumen> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<HabitacionResumen> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<ServicioResumen> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioResumen> servicios) {
        this.servicios = servicios;
    }

    public PagoResumen getPago() {
        return pago;
    }

    public void setPago(PagoResumen pago) {
        this.pago = pago;
    }
}
