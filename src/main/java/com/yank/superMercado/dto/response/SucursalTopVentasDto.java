package com.yank.superMercado.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalTopVentasDto {
    private Long idSucursal;
    private String nombre;
    private String direccion;
    private Integer totalVentas;
}
