package com.example.hotel.ai.tools;

import com.example.hotel.entities.Habitacion;
import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.ReservaHabitacion;
import com.example.hotel.entities.Usuario;

import com.example.hotel.services.HabitacionService;
import com.example.hotel.services.ReservaHabitacionService;
import com.example.hotel.services.ReservaService;

import com.example.hotel.util.RolReserva;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReservaTool {

    private final ReservaService reservaService;
    private final ReservaHabitacionService reservaHabitacionService;
    private final HabitacionService habitacionService;

    public ReservaTool(
            ReservaService reservaService,
            ReservaHabitacionService reservaHabitacionService,
            HabitacionService habitacionService) {

        this.reservaService = reservaService;
        this.reservaHabitacionService = reservaHabitacionService;
        this.habitacionService = habitacionService;
    }

    // ==========================================================
    // SOLICITAR RESERVA
    // ==========================================================

    @Transactional
    public String solicitarReservaHabitacion(
            Integer idHabitacion,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Usuario usuario) {

        // ======================================================
        // 1. VALIDAR DATOS
        // ======================================================

        if (idHabitacion == null) {

            throw new RuntimeException(
                    "Debe indicar una habitación."
            );
        }

        if (fechaInicio == null || fechaFin == null) {

            throw new RuntimeException(
                    "Debe indicar la fecha de ingreso y salida."
            );
        }

        LocalDate fechaActual = LocalDate.now();

        if (fechaInicio.isBefore(fechaActual)) {

            throw new RuntimeException(
                    "La fecha de ingreso no puede ser anterior a hoy."
            );
        }

        if (!fechaFin.isAfter(fechaInicio)) {

            throw new RuntimeException(
                    "La fecha de salida debe ser posterior a la fecha de ingreso."
            );
        }

        if (usuario == null) {

            throw new RuntimeException(
                    "Debe iniciar sesión para realizar una reserva."
            );
        }

        // ======================================================
        // 2. BUSCAR HABITACIÓN
        // ======================================================

        Habitacion habitacion =
                habitacionService
                        .getHabitacionById(idHabitacion)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "La habitación seleccionada no existe."
                                )
                        );

        // ======================================================
        // 3. VALIDAR DISPONIBILIDAD REAL
        // ======================================================

        boolean disponible =
                reservaHabitacionService.habitacionDisponible(
                        idHabitacion,
                        fechaInicio,
                        fechaFin
                );

        if (!disponible) {

            return "RESERVA_NO_CREADA. "
                    + "La habitación "
                    + habitacion.getNombre_habitacion()
                    + " no está disponible entre "
                    + fechaInicio
                    + " y "
                    + fechaFin
                    + ". No se realizó ninguna reserva.";
        }

        // ======================================================
        // 4. OBTENER PRECIO REAL DESDE LA BD
        // ======================================================

        if (habitacion.getCategoriaHabitacion() == null) {

            throw new RuntimeException(
                    "La habitación no tiene una categoría asignada."
            );
        }

        BigDecimal precioPorNoche =
                habitacion
                        .getCategoriaHabitacion()
                        .getPrecio();

        if (precioPorNoche == null) {

            throw new RuntimeException(
                    "No se encontró el precio de la habitación."
            );
        }

        precioPorNoche =
                precioPorNoche.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        // ======================================================
        // 5. CALCULAR NÚMERO DE NOCHES
        // ======================================================

        long numeroNoches =
                ChronoUnit.DAYS.between(
                        fechaInicio,
                        fechaFin
                );

        if (numeroNoches <= 0) {

            throw new RuntimeException(
                    "La reserva debe tener como mínimo una noche."
            );
        }

        // ======================================================
        // 6. CALCULAR TOTAL
        //
        // TOTAL = PRECIO POR NOCHE × NÚMERO DE NOCHES
        // ======================================================

        BigDecimal total =
                precioPorNoche.multiply(
                        BigDecimal.valueOf(numeroNoches)
                );

        total =
                total.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        System.out.println(
                "[RESERVA] Habitación: "
                        + habitacion.getNombre_habitacion()
        );

        System.out.println(
                "[RESERVA] Fecha ingreso: "
                        + fechaInicio
        );

        System.out.println(
                "[RESERVA] Fecha salida: "
                        + fechaFin
        );

        System.out.println(
                "[RESERVA] Número de noches: "
                        + numeroNoches
        );

        System.out.println(
                "[RESERVA] Precio por noche: S/ "
                        + precioPorNoche
        );

        System.out.println(
                "[RESERVA] Total calculado: S/ "
                        + total
        );

        // ======================================================
        // 7. CREAR RESERVA
        // ======================================================

        Reserva reserva =
                new Reserva();

        reserva.setFechaCreacion(
                fechaActual
        );

        reserva.setUsuario(
                usuario
        );

        reserva.setEstado(
                RolReserva.PENDIENTE
        );

        Reserva reservaGuardada =
                reservaService.insUpdReserva(
                        reserva
                );

        // ======================================================
        // 8. RELACIONAR HABITACIÓN CON LA RESERVA
        // ======================================================

        ReservaHabitacion reservaHabitacion =
                new ReservaHabitacion();

        reservaHabitacion.setFechaInicio(
                fechaInicio
        );

        reservaHabitacion.setFechaFin(
                fechaFin
        );

        /*
         * IMPORTANTE:
         *
         * Conservamos el comportamiento actual de tu BD.
         *
         * precioUnitario = precio por noche.
         *
         * El TOTAL se calcula a partir de:
         *
         * precioPorNoche × numeroNoches
         */

        reservaHabitacion.setPrecioUnitario(
                precioPorNoche
        );

        reservaHabitacion.setReserva(
                reservaGuardada
        );

        reservaHabitacion.setHabitacion(
                habitacion
        );

        reservaHabitacionService
                .insUpdReservaHabit(
                        reservaHabitacion
                );

        // ======================================================
        // 9. RESULTADO PARA LA IA
        // ======================================================

        return "RESERVA_CREADA. "
                + "Número de reserva: "
                + reservaGuardada.getId_reserva()
                + ". Habitación: "
                + habitacion.getNombre_habitacion()
                + ". ID habitación: "
                + habitacion.getId_habitacion()
                + ". Fecha de ingreso: "
                + fechaInicio
                + ". Fecha de salida: "
                + fechaFin
                + ". Número de noches: "
                + numeroNoches
                + ". Precio por noche: S/ "
                + precioPorNoche
                + ". Total de la estadía: S/ "
                + total
                + ". Estado: PENDIENTE.";
    }
}