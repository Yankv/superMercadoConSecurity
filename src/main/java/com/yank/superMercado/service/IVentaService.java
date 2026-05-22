package com.yank.superMercado.service;

import java.time.LocalDate;
import java.util.List;

import com.yank.superMercado.dto.request.ActualizarVentaRequest;
import com.yank.superMercado.dto.request.CrearVentaRequest;
import com.yank.superMercado.dto.response.VentaResponse;

public interface IVentaService {
    /**
     * Crea una nueva venta.
     * 
     * @param ventaDto
     * @return La venta creada.
     */
    VentaResponse crearVenta(CrearVentaRequest ventaDto);

    /**
     * Obtiene todas las ventas.
     * 
     * @return Una lista con todas las ventas.
     */
    List<VentaResponse> obtenerVentas();

    /**
     * Obtiene una venta por su ID.
     * 
     * @param id El ID de la venta.
     * @return La venta encontrada o null si no se encuentra.
     */
    VentaResponse obtenerVentaPorId(Long id);

    /**
     * Obtiene las ventas de una sucursal en una fecha específica.
     * 
     * @param sucursalId El ID de la sucursal.
     * @param fecha      La fecha de las ventas.
     * @return Una lista con las ventas encontradas.
     */
    List<VentaResponse> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha);

    /**
     * Actualiza los datos de una venta existente.
     * 
     * @param id       El ID de la venta a actualizar.
     * @param ventaDto Los nuevos datos de la venta.
     * @return La venta actualizada.
     */
    VentaResponse actualizarVenta(Long id, ActualizarVentaRequest ventaDto);

    /**
     * Elimina una venta por su ID.
     * 
     * @param id El ID de la venta a eliminar.
     */
    void eliminarVenta(Long id);
}
