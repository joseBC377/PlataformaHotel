package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
//import java.time.LocalDateTime;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import org.hibernate.annotations.ManyToAny;
//import org.springframework.format.annotation.DateTimeFormat;
// import jakarta.persistence.ManyToOne;
//import jakarta.validation.constraints.NotBlank;
//import com.example.hotel.util.RolMetodoPago;
// import jakarta.persistence.OneToMany;
//import jakarta.validation.constraints.PastOrPresent;
//import jakarta.persistence.Lob;
import com.example.hotel.util.RolEstadoPago;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Getter y setter 
@NoArgsConstructor // contructor vacio
@AllArgsConstructor //constructor lleno
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal total;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El igv es obligatorio")
    private BigDecimal igv;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    @NotNull(message = "El estado del pago es obligatorio")
    private RolEstadoPago estado;
    
    @Column(nullable = false)
    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;


    // @ManyToOne
    // @JsonIgnoreProperties("pago") //para que no se muestre en mi json lo de reserva
    // @JoinColumn(name = "id_reserva", nullable = false)
    // private Reserva reserva;

    // Cambio a la relación uno a uno para que un pago sea de una reserva 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonIgnoreProperties("pago")
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    @JsonIgnoreProperties({"usuario", "metodoPago", "reserva", "contacto", "resena"})
    private MetodoPago metodoPago;

}
