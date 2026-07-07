package com.example.hotel.DTOS;

import java.math.BigDecimal;

public class DetalleServicioRequest {
    private Integer idServicio;
    private BigDecimal subtotal;

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}