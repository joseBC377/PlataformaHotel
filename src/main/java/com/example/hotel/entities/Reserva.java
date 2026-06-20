package com.example.hotel.entities;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.validation.constraints.FutureOrPresent;
//import java.time.LocalDateTime;
//import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import com.example.hotel.util.RolReserva;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESERVA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer id_reserva;

    
    @Column(nullable = false)
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    private LocalDate fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Ingrese el estado de la reserva")
    private RolReserva estado;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "El usuario es obligatorio")
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("reserva")
    private Usuario usuario;

    
    @OneToOne(mappedBy = "reserva", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reserva")
    private Pago pago;

    @OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reserva")
    private List<ReservaServicio> reservaServicio;

    @OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reserva")    
    private List<ReservaHabitacion> reservaHabitacion;
}
