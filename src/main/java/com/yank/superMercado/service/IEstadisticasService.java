package com.yank.superMercado.service;

import java.util.List;

import com.yank.superMercado.dto.response.ProductoTopVentasDto;
import com.yank.superMercado.dto.response.SucursalTopVentasDto;
import com.yank.superMercado.dto.response.ResumenVentas;

public interface IEstadisticasService {
    /**
     * Obtiene la sucursal con el mayor monto total de ventas.
     *
     * @return DTO con la información de la sucursal que más ventas generó.
     */
    SucursalTopVentasDto obtenerSucursalConMasVentas();

    /**
     * Obtiene las N sucursales con mayores ventas.
     *
     * @param limite Número máximo de sucursales a devolver.
     * @return Lista de DTOs con las sucursales ordenadas por total de ventas.
     */
    List<SucursalTopVentasDto> obtenerTopNSucursalesConMasVentas(int limite);

    /**
     * Obtiene el producto con mayor cantidad vendida.
     *
     * @return DTO con la información del producto más vendido.
     */
    ProductoTopVentasDto obtenerProductoMasVendido();

    /**
     * Obtiene los N productos más vendidos.
     *
     * @param limite Número máximo de productos a devolver.
     * @return Lista de DTOs con los productos ordenados por volumen de ventas.
     */
    List<ProductoTopVentasDto> obtenerTopProductosMasVendidos(int limite);

    /**
     * Obtiene un resumen de todas las ventas.
     * 
     * @return Total de ventas realizdas y total de ingresos recibidos.
     */
    ResumenVentas obtenerResumenVentas();
}
