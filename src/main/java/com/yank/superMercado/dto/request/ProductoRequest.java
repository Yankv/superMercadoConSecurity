package com.yank.superMercado.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @Positive(message = "El precio del producto debe ser un valor positivo")
    private Double precio;

    @NotBlank(message = "La categoría del producto no puede estar vacía")
    private String categoria;

    @NotNull(message = "El stock del producto no puede ser nulo")
    @Min(value = 0, message = "El stock del producto no puede ser negativo")
    private Integer stock;
}
