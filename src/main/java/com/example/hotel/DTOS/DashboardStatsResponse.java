package com.example.hotel.DTOS;

import java.math.BigDecimal;
import java.util.List;

public class DashboardStatsResponse {
    private BigDecimal ingresosMes;
    private Double porcentajeVariacionIngresos;
    private Double porcentajeOcupacion;
    private Integer habitacionesOcupadas;
    private Integer habitacionesTotal;
    private Long reservasMes;
    private List<IngresoMensual> ingresosPorMes;
    private EstadoHabitacionesResumen estadoHabitaciones;
    private List<LlegadaResumen> proximasLlegadas;
    private Long totalClientes;

    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long t) {
        this.totalClientes = t;
    }

    // getters y setters
    public BigDecimal getIngresosMes() {
        return ingresosMes;
    }

    public void setIngresosMes(BigDecimal ingresosMes) {
        this.ingresosMes = ingresosMes;
    }

    public Double getPorcentajeVariacionIngresos() {
        return porcentajeVariacionIngresos;
    }

    public void setPorcentajeVariacionIngresos(Double v) {
        this.porcentajeVariacionIngresos = v;
    }

    public Double getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void setPorcentajeOcupacion(Double p) {
        this.porcentajeOcupacion = p;
    }

    public Integer getHabitacionesOcupadas() {
        return habitacionesOcupadas;
    }

    public void setHabitacionesOcupadas(Integer h) {
        this.habitacionesOcupadas = h;
    }

    public Integer getHabitacionesTotal() {
        return habitacionesTotal;
    }

    public void setHabitacionesTotal(Integer h) {
        this.habitacionesTotal = h;
    }

    public Long getReservasMes() {
        return reservasMes;
    }

    public void setReservasMes(Long r) {
        this.reservasMes = r;
    }

    public List<IngresoMensual> getIngresosPorMes() {
        return ingresosPorMes;
    }

    public void setIngresosPorMes(List<IngresoMensual> i) {
        this.ingresosPorMes = i;
    }

    public EstadoHabitacionesResumen getEstadoHabitaciones() {
        return estadoHabitaciones;
    }

    public void setEstadoHabitaciones(EstadoHabitacionesResumen e) {
        this.estadoHabitaciones = e;
    }

    public List<LlegadaResumen> getProximasLlegadas() {
        return proximasLlegadas;
    }

    public void setProximasLlegadas(List<LlegadaResumen> p) {
        this.proximasLlegadas = p;
    }
}