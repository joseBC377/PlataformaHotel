package com.example.hotel.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoResumen {
  private BigDecimal total;
    private BigDecimal igv;
    private String estadoPago;
    private LocalDate fechaPago;
    private String tipoMetodoPago;

    public PagoResumen(BigDecimal total, BigDecimal igv, String estadoPago, LocalDate fechaPago, String tipoMetodoPago) {
        this.total = total;
        this.igv = igv;
        this.estadoPago = estadoPago;
        this.fechaPago = fechaPago;
        this.tipoMetodoPago = tipoMetodoPago;
    }

    public BigDecimal getTotal() { return total; }
    public BigDecimal getIgv() { return igv; }
    public String getEstadoPago() { return estadoPago; }
    public LocalDate getFechaPago() { return fechaPago; }
    public String getTipoMetodoPago() { return tipoMetodoPago; }
}