package com.example.hotel.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.hotel.util.RolEstadoPago;

public class PagoRequest {
    private BigDecimal total;
    private BigDecimal igv;
    private RolEstadoPago estadoPago;
    private LocalDate fechaPago;
    private MetodoPagoRequest metodoPago; // ahora es el objeto completo, no un ID

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    public RolEstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(RolEstadoPago estadoPago) { this.estadoPago = estadoPago; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public MetodoPagoRequest getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPagoRequest metodoPago) { this.metodoPago = metodoPago; }
}