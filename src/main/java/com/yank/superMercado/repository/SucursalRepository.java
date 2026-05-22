package com.yank.superMercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yank.superMercado.model.Sucursal;
import com.yank.superMercado.projection.SucursalTopVentasProjection;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    @Query("""
            SELECT
                v.sucursal.id as idSucursal,
                v.sucursal.nombre as nombre,
                v.sucursal.direccion as direccion,
                COUNT(v.id) as totalVentas
            FROM Venta v
            WHERE v.estado = 'PAGADA'
            GROUP BY
                v.sucursal.id,
                v.sucursal.nombre,
                v.sucursal.direccion
            ORDER BY totalVentas DESC
            LIMIT :limite
            """)
    List<SucursalTopVentasProjection> findTopSucursalesConMasVentas(@Param("limite") int limite);
}
