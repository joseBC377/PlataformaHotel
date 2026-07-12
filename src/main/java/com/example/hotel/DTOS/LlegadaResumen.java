package com.example.hotel.DTOS;

import java.time.LocalDate;

public class LlegadaResumen {
    private String nombreHuesped;
    private String iniciales;
    private String habitacion;
    private String categoria;
    private LocalDate fechaInicio;

    public LlegadaResumen(String nombreHuesped, String iniciales, String habitacion, String categoria, LocalDate fechaInicio) {
        this.nombreHuesped = nombreHuesped;
        this.iniciales = iniciales;
        this.habitacion = habitacion;
        this.categoria = categoria;
        this.fechaInicio = fechaInicio;
    }

    public String getNombreHuesped() { return nombreHuesped; }
    public String getIniciales() { return iniciales; }
    public String getHabitacion() { return habitacion; }
    public String getCategoria() { return categoria; }
    public LocalDate getFechaInicio() { return fechaInicio; }
}