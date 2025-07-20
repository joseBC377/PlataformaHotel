package com.example.hotel.entities;

import java.math.BigDecimal;

import com.example.hotel.entities.Habitacion.HabitacionBuilder;

// import java.util.List;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORIA_HABITACION")
public class CategoriaHabitacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "El nombre no puede estar vacío")
  @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
  @Column(unique = true, nullable = false, length = 50)
  private String nombre;

  @Lob
  @NotBlank(message = "La descripción no puede estar vacía")
  @Column(columnDefinition = "TEXT", nullable = false)
  private String descripcion;

  @Column(nullable = false)
  @Min(value = 1, message = "La capacidad mínima debe ser 1")
  private Integer capacidad;

  @NotNull(message = "El precio es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
  private BigDecimal precio;

  @Column(columnDefinition = "LONGTEXT") 
  private String imagen;
  // Añadir carga de llave foranea de habitacion
  // @OneToMany(mappedBy = "categoria_Habitacion", cascade =
  // CascadeType.ALL,orphanRemoval = true)
  // @JsonIgnoreProperties("categoria_Habitacion")
  // private List<Habitacion> habitacion;



}
