package com.example.hotel.DTOS;

import com.example.hotel.util.RolMetodoPago;

public class MetodoPagoRequest {
    private RolMetodoPago tipo;
    private String ultimoscuatrodigitos; // solo aplica si tipo = TARJETA
    private String fechaVencimiento;      // solo aplica si tipo = TARJETA

    public RolMetodoPago getTipo() { return tipo; }
    public void setTipo(RolMetodoPago tipo) { this.tipo = tipo; }
    public String getUltimoscuatrodigitos() { return ultimoscuatrodigitos; }
    public void setUltimoscuatrodigitos(String ultimoscuatrodigitos) { this.ultimoscuatrodigitos = ultimoscuatrodigitos; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}