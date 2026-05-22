package com.yank.superMercado.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.yank.superMercado.enums.EstadoVenta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearVentaRequest {
    @NotNull(message = "La fecha de la venta no puede estar nulo")
    private LocalDate fecha;

    @NotNull(message = "El estado de la venta no puede ser nulo")
    private EstadoVenta estado;

    private Double total;

    // Datos de la sucursal
    @NotNull(message = "El ID de la sucursal no puede ser nulo")
    private Long idSucursal;

    // Lista de detalles de venta
    @Valid
    @NotNull(message = "La lista de detalles de venta no puede ser nula")
    private List<DetalleVentaRequest> detalles;
}
