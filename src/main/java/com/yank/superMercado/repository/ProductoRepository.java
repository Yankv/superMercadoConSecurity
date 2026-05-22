package com.yank.superMercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yank.superMercado.model.Producto;
import com.yank.superMercado.projection.ProductoTopVentasProjection;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, ProductoRepositoryC {
    @Query("""
            SELECT
                d.producto.id as idProducto,
                d.producto.nombre as nombre,
                d.producto.categoria as categoria,
                d.producto.precio as precio,
                SUM(d.cantidad) as totalUnidadesVendidas
            FROM DetalleVenta d
            WHERE d.venta.estado = 'PAGADA'
            GROUP BY
                d.producto.id,
                d.producto.nombre,
                d.producto.categoria,
                d.producto.precio
            ORDER BY totalUnidadesVendidas DESC
            LIMIT :limite
            """)
    List<ProductoTopVentasProjection> findTopProductosMasVendidos(@Param("limite") int limite);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
            UPDATE Producto p
            SET p.stock = :stock
            WHERE p.id = :id
            """)
    int actualizarStock(@Param("id") Long id, @Param("stock") Integer stock);
}
