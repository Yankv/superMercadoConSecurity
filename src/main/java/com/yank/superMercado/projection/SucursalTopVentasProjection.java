package com.yank.superMercado.projection;

public interface SucursalTopVentasProjection {
    Long getIdSucursal();
    String getNombre();
    String getDireccion();
    Integer getTotalVentas();
}
