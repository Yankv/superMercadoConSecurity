package com.yank.superMercado.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoTopVentasDto {
    private Long idProducto;
    private String nombre;
    private String categoria;
    private Double precio;
    private Long totalUnidadesVendidas;
}
