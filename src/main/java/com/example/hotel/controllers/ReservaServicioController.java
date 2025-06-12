package com.example.hotel.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.ReservaServicioId;
import com.example.hotel.entities.ReservaServicio;
import com.example.hotel.entities.Servicio;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.repositories.ReservaServicioRepository;
import com.example.hotel.repositories.ServicioRepository;
import com.example.hotel.services.ReservaServicioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "api/ReservaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReservaServicioController {

        private ReservaRepository reservaRepository;
        private ServicioRepository servicioRepository;
        private ReservaServicioRepository reservaServicioRepository;

        private final ReservaServicioService reservaServicioService;

        @GetMapping("lista")
        public List<ReservaServicio> selectReservaServicios() {
                return reservaServicioService.listarTodas();
        }

        @PostMapping("insertar")
        public ResponseEntity<ReservaServicio> insertarReservaServicio(@RequestBody ReservaServicio reservaServicio) {
                // Obtener las entidades reales desde la base de datos
                Reserva reserva = reservaRepository.findById(reservaServicio.getReserva().getId()) // obtengo el objeto reserva ,despues el getId captura el id de ese objeto reserva
                                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

                Servicio servicio = servicioRepository.findById(reservaServicio.getServicio().getId_servicio())
                                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

                reservaServicio.setReserva(reserva); // Recupero un objeto reserva de la database es decir la variable
                                                     // reserva me devuele un objeto que busque
                reservaServicio.setServicio(servicio);

                // Crear y setear el ID compuesto
                ReservaServicioId id = new ReservaServicioId(reserva.getId(), servicio.getId_servicio());
                reservaServicio.setId(id);

                ReservaServicio guardado = reservaServicioService.guardar(reservaServicio);
                return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        }

        @PutMapping("editar/{idReserva}/{idServicio}")
        public ResponseEntity<ReservaServicio> editarReservaServicio(
                        @PathVariable Integer idReserva,
                        @PathVariable Integer idServicio,
                        @RequestBody ReservaServicio nuevaRelacion) {

                // El ID actual que modificaremos
                ReservaServicioId idActual = new ReservaServicioId(idReserva, idServicio);

                // ReservaServicio existente = reservaServicioRepository.findById(idActual)
                // .orElseThrow(() -> new RuntimeException("Relación no encontrada"));
                if (!reservaServicioRepository.existsById(idActual)) {
                        throw new RuntimeException("Relación no encontrada");
                }
                // Obtener los nuevos objetos desde el JSON (pueden ser diferentes a los del
                // path)
                Reserva nuevaReserva = reservaRepository.findById(nuevaRelacion.getReserva().getId())
                                .orElseThrow(() -> new RuntimeException("Nueva reserva no encontrada"));

                Servicio nuevoServicio = servicioRepository.findById(nuevaRelacion.getServicio().getId_servicio())
                                .orElseThrow(() -> new RuntimeException("Nuevo servicio no encontrado"));

                // Eliminar la relación vieja
                reservaServicioRepository.deleteById(idActual);

                // Crear nueva relación , porque no podemos quedarnos con el IdCompuesto y que los datos nomas cambien porque forman parte del Id Completo todo
                ReservaServicioId nuevoId = new ReservaServicioId(nuevaReserva.getId(), nuevoServicio.getId_servicio());
                nuevaRelacion.setId(nuevoId);
                nuevaRelacion.setReserva(nuevaReserva);
                nuevaRelacion.setServicio(nuevoServicio);

                ReservaServicio guardado = reservaServicioRepository.save(nuevaRelacion);

                return ResponseEntity.ok(guardado);
        }

        @DeleteMapping("eliminar/{idReserva}/{idServicio}")
        public ResponseEntity<Void> eliminarReservaServicio(
                        @PathVariable Integer idReserva,
                        @PathVariable Integer idServicio) {

                // Buscar la relación en la base de datos
                ReservaServicio reservaServicio = reservaServicioRepository
                                .findById(new ReservaServicioId(idReserva, idServicio))
                                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));

                // Eliminar la relación
                reservaServicioRepository.delete(reservaServicio);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

}