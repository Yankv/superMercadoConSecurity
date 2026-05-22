package com.yank.superMercado.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaResponse {
    private Long id;

    private Integer cantidad;

    private String nombreProducto;

    private Double precioUnitario;

    private Double subTotal;
}
