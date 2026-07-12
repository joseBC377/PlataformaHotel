package com.example.hotel.DTOS;

public class EstadoHabitacionesResumen {
    private long limpias;
    private long enLimpieza;
    private long mantenimiento;
    private long sucias;

    public EstadoHabitacionesResumen(long limpias, long enLimpieza, long mantenimiento, long sucias) {
        this.limpias = limpias;
        this.enLimpieza = enLimpieza;
        this.mantenimiento = mantenimiento;
        this.sucias = sucias;
    }

    public long getLimpias() { return limpias; }
    public long getEnLimpieza() { return enLimpieza; }
    public long getMantenimiento() { return mantenimiento; }
    public long getSucias() { return sucias; }
}