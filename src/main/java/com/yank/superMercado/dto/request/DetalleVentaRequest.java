package com.yank.superMercado.dto.request;

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
public class DetalleVentaRequest {
    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser un valor positivo")
    private Integer cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @Positive(message = "El precio unitario debe ser un valor positivo")
    private Double precioUnitario;

    @NotNull(message = "El producto no puede ser nulo")
    private Long productoId;
}
