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
@Table(name = "METODO_PAGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "El tipo de pago es obligatorio")
    private RolMetodoPago tipo;

    @Column(length = 4)
    @Pattern(regexp = "\\d{4}", message = "Debe contener 4 dígitos")
    private String ultimoscuatrodigitos;

    @Column(length = 5)
    @Pattern(regexp = "(0[1-9]|1[0-2])\\/\\d{2}", message = "Formato MM/YY")
    private String fechaVencimiento; 

    @Column(length = 250)
    private String token;

    @Column(nullable = false)
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"metodoPago", "reserva", "contacto", "resena"})
    private Usuario usuario;
}