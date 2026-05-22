package com.yank.superMercado.projection;

public interface ProductoTopVentasProjection {
    Long getIdProducto();
    String getNombre();
    String getCategoria();
    Double getPrecio();
    Long getTotalUnidadesVendidas();
}
