package com.example.hotel.entities;

import com.example.hotel.util.RolMetodoPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "tarjeta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(nullable = false, length = 250)
    private String token;

    @Column(nullable = false, length = 5)
    private String fechaVencimiento;

    @Column(nullable = false, length = 4)
    private String ultimoscuatrodigitos;

    @OneToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;
}
