package com.example.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service 
public class PowerAutomateSyncService {

    @Autowired 
    private JavaMailSender mailSender;

    @Async 
    public void sincronizarReserva(
            Long idReserva,
            String nombreHuesped,
            String correoHuesped,
            String fechaCreacion,
            String fechaEntrada,
            String fechaSalida,
            long noches,
            String habitacionNombre,
            String estado
    ) {
        try {
            // Estructura idéntica a la muestra procesada en Power Automate
            String jsonPayload = String.format(
                "{\"id_reserva\": %d, \"huesped\": \"%s\", \"correo\": \"%s\", \"fechaCreacion\": \"%s\", \"fechaEntrada\": \"%s\", \"fechaSalida\": \"%s\", \"noches\": %d, \"habitacion\": \"%s\", \"estado\": \"%s\"}",
                idReserva,
                nombreHuesped,
                correoHuesped,
                fechaCreacion,
                fechaEntrada,
                fechaSalida,
                noches,
                habitacionNombre,
                estado
            );

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("reservas.royal.suites@gmail.com");
            mensaje.setTo("U22215095@utp.edu.pe"); // Cuenta donde escucha el desencadenador V3
            mensaje.setSubject("SYNC_RESERVA_HOTEL");
            mensaje.setText(jsonPayload);

            mailSender.send(mensaje);
            System.out.println(">>> [Power Automate] Notificación enviada para la reserva #" + idReserva);

        } catch (Exception e) {
            System.err.println(">>> [Power Automate] Error al enviar correo de sincronización: " + e.getMessage());
        }
    }
}