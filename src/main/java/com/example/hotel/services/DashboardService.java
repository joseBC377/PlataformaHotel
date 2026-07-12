package com.example.hotel.services;

import com.example.hotel.DTOS.*;
import com.example.hotel.entities.Pago;
import com.example.hotel.entities.ReservaHabitacion;
import com.example.hotel.repositories.*;
import com.example.hotel.util.RolHabitacion;
import com.example.hotel.util.RolTipo;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;
    private final HabitacionRepository habitacionRepository;
    private final ReservaHabitacionRepository reservaHabitacionRepository;
    private final UsuarioRepository usuarioRepository;
    @Transactional(readOnly = true)
    public DashboardStatsResponse obtenerEstadisticas() {
        DashboardStatsResponse response = new DashboardStatsResponse();
        // --- Total de clientes registrados ---
        response.setTotalClientes((long) usuarioRepository.TodosClient().size());
        LocalDate hoy = LocalDate.now();
        YearMonth mesActual = YearMonth.from(hoy);
        YearMonth mesAnterior = mesActual.minusMonths(1);

        // --- Ingresos del mes actual y del mes anterior ---
        BigDecimal ingresosMes = sumarPagosDeMes(mesActual);
        BigDecimal ingresosMesAnterior = sumarPagosDeMes(mesAnterior);
        response.setIngresosMes(ingresosMes);

        double variacion = 0.0;
        if (ingresosMesAnterior.compareTo(BigDecimal.ZERO) > 0) {
            variacion = ingresosMes.subtract(ingresosMesAnterior)
                    .divide(ingresosMesAnterior, 4, java.math.RoundingMode.HALF_UP)
                    .doubleValue() * 100;
        }
        response.setPorcentajeVariacionIngresos(variacion);

        // --- Ocupación de habitaciones ---
        long ocupadas = habitacionRepository.countByEstado(RolHabitacion.OCUPADA);
        long totalHabitaciones = habitacionRepository.count();
        response.setHabitacionesOcupadas((int) ocupadas);
        response.setHabitacionesTotal((int) totalHabitaciones);
        response.setPorcentajeOcupacion(totalHabitaciones > 0 ? (ocupadas * 100.0 / totalHabitaciones) : 0.0);

        // --- Reservas del mes ---
        LocalDate inicioMes = mesActual.atDay(1);
        LocalDate finMes = mesActual.atEndOfMonth();
        response.setReservasMes(reservaRepository.contarPorRangoFechas(inicioMes, finMes));

        // --- Gráfico: ingresos de los últimos 6 meses ---
        List<IngresoMensual> ingresosPorMes = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth mes = mesActual.minusMonths(i);
            BigDecimal total = sumarPagosDeMes(mes);
            String nombreMes = mes.getMonth().getDisplayName(TextStyle.SHORT, new Locale("es", "ES")).toUpperCase();
            ingresosPorMes.add(new IngresoMensual(nombreMes, total));
        }
        response.setIngresosPorMes(ingresosPorMes);

        // --- Estado de habitaciones (por tipo: limpieza) ---
        long limpias = habitacionRepository.countByTipo(RolTipo.LIMPIO);
        long enLimpieza = habitacionRepository.countByTipo(RolTipo.EN_LIMPIEZA);
        long mantenimiento = habitacionRepository.countByTipo(RolTipo.MANTENIMIENTO);
        long sucias = habitacionRepository.countByTipo(RolTipo.SUCIO);
        response.setEstadoHabitaciones(new EstadoHabitacionesResumen(limpias, enLimpieza, mantenimiento, sucias));

        // --- Próximas llegadas (check-ins de hoy) ---
        List<LlegadaResumen> llegadas = new ArrayList<>();
        List<ReservaHabitacion> llegadasHoy = reservaHabitacionRepository.buscarPorFechaInicio(hoy);
        for (ReservaHabitacion rh : llegadasHoy) {
            String nombreCompleto = rh.getReserva().getUsuario().getNombre_usuario() + " " +
                    rh.getReserva().getUsuario().getApellido_paterno();
            String iniciales = "" + rh.getReserva().getUsuario().getNombre_usuario().charAt(0) +
                    rh.getReserva().getUsuario().getApellido_paterno().charAt(0);
            llegadas.add(new LlegadaResumen(
                    nombreCompleto,
                    iniciales.toUpperCase(),
                    rh.getHabitacion().getNombre_habitacion(),
                    rh.getHabitacion().getCategoriaHabitacion().getNombre_categoria(),
                    rh.getFechaInicio()));
        }
        response.setProximasLlegadas(llegadas);

        return response;
    }

    private BigDecimal sumarPagosDeMes(YearMonth mes) {
        List<Pago> pagos = pagoRepository.buscarPorRangoFechas(mes.atDay(1), mes.atEndOfMonth());
        return pagos.stream()
                .map(Pago::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}