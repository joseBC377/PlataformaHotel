package com.example.hotel.DTOS;

import java.math.BigDecimal;

public class IngresoMensual {
    private String mes;
    private BigDecimal total;

    public IngresoMensual(String mes, BigDecimal total) {
        this.mes = mes;
        this.total = total;
    }

    public String getMes() { return mes; }
    public BigDecimal getTotal() { return total; }
}