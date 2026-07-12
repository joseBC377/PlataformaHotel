package com.example.hotel.DTOS;

import java.math.BigDecimal;

public class ServicioResumen {
   private String nombreServicio;
    private BigDecimal precio;

    public ServicioResumen(String nombreServicio, BigDecimal precio) {
        this.nombreServicio = nombreServicio;
        this.precio = precio;
    }

    public String getNombreServicio() { return nombreServicio; }
    public BigDecimal getPrecio() { return precio; }
}