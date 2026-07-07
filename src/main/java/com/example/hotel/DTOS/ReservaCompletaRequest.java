package com.example.hotel.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservaCompletaRequest {
    private Integer idUsuario;
    private List<DetalleHabitacionRequest> habitaciones;
    private List<DetalleServicioRequest> servicios;
    private PagoRequest pago;

    // getters y setters
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public List<DetalleHabitacionRequest> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<DetalleHabitacionRequest> habitaciones) { this.habitaciones = habitaciones; }
    public List<DetalleServicioRequest> getServicios() { return servicios; }
    public void setServicios(List<DetalleServicioRequest> servicios) { this.servicios = servicios; }
    public PagoRequest getPago() { return pago; }
    public void setPago(PagoRequest pago) { this.pago = pago; }
}