package com.yank.superMercado.service;

import java.util.List;

import com.yank.superMercado.dto.request.SucursalRequest;
import com.yank.superMercado.dto.response.SucursalResponse;

public interface ISucursalService {
    /**
     * Crea una nueva sucursal.
     * 
     * @param sucursalDto Los datos de la sucursal a crear.
     * @return La sucursal creada.
     */
    SucursalResponse crearSucursal(SucursalRequest sucursalDto);

    /**
     * Obtiene todas las sucursales.
     * 
     * @return Una lista con todas las sucursales.
     */
    List<SucursalResponse> obtenerSucursales();

    /**
     * Obtiene una sucursal por su ID.
     * 
     * @param id El ID de la sucursal.
     * @return La sucursal encontrada o null si no se encuentra.
     */
    SucursalResponse obtenerSucursalPorId(Long id);

    

    /**
     * Actualiza los datos de una sucursal existente.
     * 
     * @param id El ID de la sucursal a actualizar.
     * @param sucursalDto Los nuevos datos de la sucursal.
     * @return La sucursal actualizada.
     */
    SucursalResponse actualizarSucursal(Long id, SucursalRequest sucursalDto);

    /**
     * Elimina una sucursal por su ID.
     * 
     * @param id El ID de la sucursal a eliminar.
     */
    void eliminarSucursal(Long id);
}
