package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.hotel.entities.Pago;
import com.example.hotel.util.RolEstadoPago;
import com.example.hotel.util.RolMetodoPago;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Getter y setter 
@NoArgsConstructor // contructor vacio
@AllArgsConstructor //constructor lleno
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;
    @Column(nullable = false)
    @NotNull
    private BigDecimal total;

    @NotBlank(message = "El igv es obligatoria")
    @Lob
    @Column(columnDefinition ="decimal(10,2)"  ,nullable = false)
    private BigDecimal igv;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private RolEstadoPago estado;
    
    @Column(nullable = false)
    private LocalDate fecha_pago;;


    // @ManyToOne
    // @JsonIgnoreProperties("pago") //para que no se muestre en mi json lo de reserva
    // @JoinColumn(name = "id_reserva", nullable = false)
    // private Reserva reserva;

    // Cambio a la relación uno a uno para que un pago sea de una reserva 
    @OneToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

}
