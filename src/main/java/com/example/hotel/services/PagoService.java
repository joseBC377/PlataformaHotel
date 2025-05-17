package com.example.hotel.services;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotel.entities.Pago;
import com.example.hotel.entities.Reserva;
import com.example.hotel.entities.Usuario;
import com.example.hotel.repositories.PagoRepository;
import com.example.hotel.repositories.ReservaRepository;
import com.example.hotel.repositories.UsuarioRepository;
import com.example.hotel.util.Pago_ReservaInfo;

import jakarta.transaction.Transactional;

@Service
public class PagoService {

    //inyeccion de dependencia por contructor
    private final PagoRepository repository;
    
    private final ReservaRepository reservaRepository;

    private final UsuarioRepository usuarioRepository;


    public PagoService (PagoRepository repository,ReservaRepository reservaRepository, UsuarioRepository usuarioRepository){
        this.repository = repository;
        this.reservaRepository=reservaRepository;
        this.usuarioRepository=usuarioRepository;


    }

    
    public List <Pago>listarTodas(){
        return repository.findAll();
    }

    public Pago insert(Pago pago){
        return repository.save(pago);
    }
    

    public Pago obtenerPorId (Integer idPago){
        return repository.findById(idPago).orElse(null);
    }

    public Pago actualizarPago(Integer idPago,Pago pago){
        if (!repository.existsById(pago.getIdPago())) {
            throw new RuntimeException("No se encontro el id "+pago.getIdPago());
        }
        return repository.save(pago);
    }

    public void eliminar (Integer id_pago){
        repository.deleteById(id_pago);
    }




   

    @Transactional
    public void crearReservaPago(Pago_ReservaInfo info){
        try {
            
            //Buscar el usuario por id
            Usuario usuario = usuarioRepository.findById(info.id_usuario()).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));


            //Crear una reserva
            Reserva reserva = new Reserva();
            reserva.setFecha_inicio(info.fecha_inicio());
            reserva.setFecha_fin(info.fecha_fin());
            reserva.setUsuario(usuario);
            Reserva aux = reservaRepository.save(reserva);

            //Crear un pago
            Pago pago = new Pago();
            pago.setTotal(info.total());
            pago.setMetodo_pago(info.metodo_pago());
            pago.setEstado_pago(info.estado_pago());
            pago.setFecha_pago(LocalDateTime.now());
            pago.setReserva(aux);
            repository.save(pago);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }

    }





}
