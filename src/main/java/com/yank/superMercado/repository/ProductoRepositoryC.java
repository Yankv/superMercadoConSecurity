package com.yank.superMercado.repository;

import java.util.List;

import com.yank.superMercado.model.Producto;

public interface ProductoRepositoryC {
    List<Producto> buscarConFiltros(String nombre, String categoria, Double precioMin, Double precioMax);
}
