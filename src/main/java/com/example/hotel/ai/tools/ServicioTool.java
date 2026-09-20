package com.example.hotel.ai.tools;

import com.example.hotel.entities.Servicio;
import com.example.hotel.services.ServicioService;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServicioTool {

    private final ServicioService servicioService;

    public ServicioTool(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    public List<Map<String, Object>> consultarServiciosHotel() {

        List<Servicio> servicios =
                servicioService.sellectAllServicios();

        List<Map<String, Object>> resultado =
                new ArrayList<>();

        for (Servicio servicio : servicios) {

            Map<String, Object> datos =
                    new LinkedHashMap<>();

            datos.put(
                    "idServicio",
                    servicio.getIdServicio()
            );

            datos.put(
                    "nombre",
                    servicio.getNombre_servicio()
            );

            datos.put(
                    "descripcion",
                    servicio.getDescripcion_servicio()
            );

            datos.put(
                    "precio",
                    servicio.getPrecio()
            );

            resultado.add(datos);
        }

        return resultado;
    }
}