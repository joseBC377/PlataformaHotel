package com.example.hotel.ai.tools;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.services.HabitacionService;
import com.example.hotel.services.ReservaHabitacionService;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class HabitacionTool {

    private final HabitacionService habitacionService;
    private final ReservaHabitacionService reservaHabitacionService;

    public HabitacionTool(
            HabitacionService habitacionService,
            ReservaHabitacionService reservaHabitacionService) {

        this.habitacionService = habitacionService;
        this.reservaHabitacionService = reservaHabitacionService;
    }

    // ==========================================================
    // CONSULTAR TODAS LAS HABITACIONES
    // ==========================================================

    public List<Map<String, Object>> consultarHabitaciones() {

        List<Habitacion> habitaciones =
                habitacionService.selectAllHabitacions();

        List<Map<String, Object>> resultado =
                new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {

            resultado.add(
                    convertirHabitacionAMapa(habitacion)
            );
        }

        return resultado;
    }

    // ==========================================================
    // CONSULTAR HABITACIONES DISPONIBLES ENTRE DOS FECHAS
    // ==========================================================

    public List<Map<String, Object>> consultarHabitacionesDisponibles(
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        if (fechaInicio == null || fechaFin == null) {

            throw new RuntimeException(
                    "Debe indicar la fecha de ingreso y la fecha de salida."
            );
        }

        if (!fechaFin.isAfter(fechaInicio)) {

            throw new RuntimeException(
                    "La fecha de salida debe ser posterior a la fecha de ingreso."
            );
        }

        List<Habitacion> habitaciones =
                habitacionService.selectAllHabitacions();

        List<Map<String, Object>> disponibles =
                new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {

            Integer idHabitacion =
                    habitacion.getId_habitacion();

            boolean disponible =
                    reservaHabitacionService.habitacionDisponible(
                            idHabitacion,
                            fechaInicio,
                            fechaFin
                    );

            if (disponible) {

                Map<String, Object> datos =
                        convertirHabitacionAMapa(habitacion);

                datos.put(
                        "disponible",
                        true
                );

                datos.put(
                        "fechaInicioConsultada",
                        fechaInicio.toString()
                );

                datos.put(
                        "fechaFinConsultada",
                        fechaFin.toString()
                );

                disponibles.add(datos);
            }
        }

        return disponibles;
    }

    // ==========================================================
    // CONVERTIR HABITACIÓN A MAPA
    // ==========================================================

    private Map<String, Object> convertirHabitacionAMapa(
            Habitacion habitacion) {

        Map<String, Object> datos =
                new LinkedHashMap<>();

        datos.put(
                "idHabitacion",
                habitacion.getId_habitacion()
        );

        datos.put(
                "nombre",
                habitacion.getNombre_habitacion()
        );

        datos.put(
                "descripcion",
                habitacion.getDescripcion_habitacion()
        );

        datos.put(
                "estado",
                habitacion.getEstado()
        );

        datos.put(
                "tipo",
                habitacion.getTipo()
        );

        if (habitacion.getCategoriaHabitacion() != null) {

            datos.put(
                    "categoria",
                    habitacion
                            .getCategoriaHabitacion()
                            .getNombre_categoria()
            );

            datos.put(
                    "capacidad",
                    habitacion
                            .getCategoriaHabitacion()
                            .getCapacidad()
            );

            datos.put(
                    "precio",
                    habitacion
                            .getCategoriaHabitacion()
                            .getPrecio()
            );
        }

        return datos;
    }
}