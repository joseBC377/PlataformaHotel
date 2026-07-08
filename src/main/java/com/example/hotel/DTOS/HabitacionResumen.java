package com.example.hotel.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HabitacionResumen {
private String nombreHabitacion;
    private String nombreCategoria;
    private String imagen;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal precioUnitario;

    public HabitacionResumen(String nombreHabitacion, String nombreCategoria, String imagen,
                              LocalDate fechaInicio, LocalDate fechaFin, BigDecimal precioUnitario) {
        this.nombreHabitacion = nombreHabitacion;
        this.nombreCategoria = nombreCategoria;
        this.imagen = imagen;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioUnitario = precioUnitario;
    }

    public String getNombreHabitacion() { return nombreHabitacion; }
    public String getNombreCategoria() { return nombreCategoria; }
    public String getImagen() { return imagen; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
}
