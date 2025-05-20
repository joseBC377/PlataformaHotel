package com.example.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false, length = 50)
    @NotNull
    @NotBlank(message="Ingrese un metodo de pago")
    @Pattern(regexp = "\\D*",message="No puede contener numeros")
    private String metodo_pago;

    @Column(nullable = false, length = 50)
    @NotNull
    @NotBlank(message ="Ingrese su estado de pago")
    @Pattern(regexp = "\\D*",message="No puede contener numeros")
    private String estado_pago;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    @PastOrPresent(message= "La fecha no puede ser futura")
    private LocalDateTime fecha_pago;

    // @ManyToOne
    // @JsonIgnoreProperties("pago") //para que no se muestre en mi json lo de reserva
    // @JoinColumn(name = "id_reserva", nullable = false)
    // private Reserva reserva;

    // Cambio a la relación uno a uno para que un pago sea de una reserva 
    @OneToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

}
