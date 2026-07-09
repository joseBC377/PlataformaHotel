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
@Table(name = "billetera_digital")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BilleteraDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_billetera")
    private Integer idBilletera;

    @Column(name = "numero_celular", nullable = false, length = 9)
    private String numeroCelular;

    @OneToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;
}