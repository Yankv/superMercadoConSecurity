package com.yank.superMercado.service;

import java.util.List;

import com.yank.superMercado.dto.request.ProductoRequest;
import com.yank.superMercado.dto.response.ProductoResponse;

public interface IProductoService {
    /**
     * Crea un nuevo producto a partir de un ProductoDto.
     * 
     * @param productoDto Los datos del prodcuto a crear.
     * @return productoDto creado.
     */
    ProductoResponse crearProducto(ProductoRequest productoDto);

    /**
     * Obtiene una lista de todos los productos disponibles.
     * 
     * @return Lista con los productos encontrados.
     */
    List<ProductoResponse> obtenerProductos();

    /**
     * Obtiene un producto por su ID.
     * 
     * @param id ID del producto a buscar.
     * @return Producto encontrado.
     */
    ProductoResponse obtenerProductoPorId(Long id);

    /**
     * Actualiza un producto existente.
     * 
     * @param id ID del producto a actualizar.
     * @param productoDto Datos del producto a actualizar.
     * @return Producto actualizado.
     */
    ProductoResponse actualizarProducto(Long id, ProductoRequest productoDto);

    /**
     * Elimina un producto por su ID.
     * 
     * @param id ID del producto a eliminar.
     */
    void eliminarProducto(Long id);

    List<ProductoResponse> obtenerProductosConFiltros(String nombre, String categoria, Double precioMin, Double precioMax);

    boolean actualizarStock(Long id, Integer stock);
}
