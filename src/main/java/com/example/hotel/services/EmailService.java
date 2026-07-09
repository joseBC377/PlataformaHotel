package com.example.hotel.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacionReserva(String destinatario, String nombreCliente, Integer idReserva,
                                            String nombreHabitacion, String nombreCategoria,
                                            LocalDate fechaInicio, LocalDate fechaFin,
                                            BigDecimal total) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject("¡Reserva Confirmada! #" + idReserva);

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
            String html = construirHtml(nombreCliente, idReserva, nombreHabitacion, nombreCategoria,
                    fechaInicio.format(formato), fechaFin.format(formato), total);

            helper.setText(html, true); // true = es HTML

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            System.err.println("Error al construir el correo: " + e.getMessage());
        }
    }

    private String construirHtml(String nombreCliente, Integer idReserva, String nombreHabitacion,
                                   String nombreCategoria, String fechaInicio, String fechaFin, BigDecimal total) {
        return """
            <!DOCTYPE html>
            <html>
            <body style="margin:0; padding:0; background-color:#f4f4f7; font-family:Arial, Helvetica, sans-serif;">
              <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f4f7; padding:30px 0;">
                <tr>
                  <td align="center">
                    <table width="500" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 2px 10px rgba(0,0,0,0.08);">

                      <!-- Encabezado -->
                      <tr>
                        <td style="background-color:#1a2e56; padding:32px; text-align:center;">
                          <div style="width:56px; height:56px; background-color:#ffffff; border-radius:50%%; margin:0 auto 16px; line-height:56px; font-size:28px;">✓</div>
                          <h1 style="color:#ffffff; font-size:22px; margin:0;">¡Reserva Confirmada!</h1>
                          <p style="color:#c7d0e0; font-size:14px; margin:8px 0 0;">Reserva #%d</p>
                        </td>
                      </tr>

                      <!-- Saludo -->
                      <tr>
                        <td style="padding:28px 32px 0;">
                          <p style="color:#1e293b; font-size:15px; margin:0 0 8px;">Hola <strong>%s</strong>,</p>
                          <p style="color:#64748b; font-size:14px; margin:0 0 24px; line-height:1.5;">
                            Tu reserva ha sido confirmada exitosamente. Aquí tienes el resumen de tu estadía:
                          </p>
                        </td>
                      </tr>

                      <!-- Detalles de la habitación -->
                      <tr>
                        <td style="padding:0 32px;">
                          <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#fafbfd; border-radius:10px; border:1px solid #f1f5f9;">
                            <tr>
                              <td style="padding:20px;">
                                <p style="color:#1a2e56; font-size:16px; font-weight:bold; margin:0 0 4px;">%s</p>
                                <p style="color:#64748b; font-size:13px; margin:0 0 16px;">%s</p>

                                <table width="100%%" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td style="padding:6px 0; color:#64748b; font-size:13px;">Check-in</td>
                                    <td style="padding:6px 0; color:#1e293b; font-size:13px; text-align:right; font-weight:600;">%s</td>
                                  </tr>
                                  <tr>
                                    <td style="padding:6px 0; color:#64748b; font-size:13px; border-top:1px solid #eee;">Check-out</td>
                                    <td style="padding:6px 0; color:#1e293b; font-size:13px; text-align:right; font-weight:600; border-top:1px solid #eee;">%s</td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Total -->
                      <tr>
                        <td style="padding:20px 32px;">
                          <table width="100%%" cellpadding="0" cellspacing="0" style="border-top:2px solid #1a2e56; padding-top:16px;">
                            <tr>
                              <td style="padding-top:16px; color:#1e293b; font-size:15px; font-weight:bold;">Total Pagado</td>
                              <td style="padding-top:16px; color:#1a2e56; font-size:20px; font-weight:bold; text-align:right;">S/ %s</td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <!-- Footer -->
                      <tr>
                        <td style="background-color:#fafbfd; padding:20px 32px; text-align:center; border-top:1px solid #f1f5f9;">
                          <p style="color:#94a3b8; font-size:12px; margin:0;">¡Te esperamos! Si tienes alguna consulta, no dudes en contactarnos.</p>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>
            </body>
            </html>
            """.formatted(idReserva, nombreCliente, nombreHabitacion, nombreCategoria, fechaInicio, fechaFin, total.toString());
    }
}