package com.yank.superMercado.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.yank.superMercado.enums.EstadoVenta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponse {
    private Long id;

    private LocalDate fecha;

    private EstadoVenta estado;

    private Double total;

    private String nombreSucursal;

    private List<DetalleVentaResponse> detalles;
}
