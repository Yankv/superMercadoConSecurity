package com.yank.superMercado.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yank.superMercado.model.Venta;
import com.yank.superMercado.projection.ResumenVentasProjection;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>, JpaSpecificationExecutor<Venta> {
    /**
     * Busca una venta por su id, validando que su estado no sea 'CANCELADA'
     * 
     * @param id ID de la venta
     * @return Venta encontrada
     */
    @Query("""
            SELECT v
            FROM Venta v
            WHERE v.id = :id
            AND v.estado <> 'CANCELADA'
            """)
    Optional<Venta> findById(@Param("id") Long id);

    /**
     * Busca las ventas validando que su estado no sea 'CANCELADA'
     * 
     * @return Lista de ventas encontradas
     */
    @Query("""
            SELECT v
            FROM Venta v
            WHERE v.estado <> 'CANCELADA'
            """)
    List<Venta> findAll();

//     /**
//      * Busca una venta por el ID de la sucursal y la fecha.
//      * 
//      * @param sucursalId ID de la sucursal a filtrar
//      * @param fecha      Fecha a filtrar
//      * @return Ventas encontradas
//      */
//     @Query("""
//             SELECT v
//             FROM Venta v
//             WHERE v.sucursal.id = :sucursalId
//             AND v.fecha = :fecha
//             AND v.estado <> 'CANCELADA'
//             """)
//     List<Venta> buscarPorSucursalIdYFecha(@Param("sucursalId") Long sucursalId, @Param("fecha") LocalDate fecha);

    /**
     * Obtiene un resumen de todas las ventas.
     * 
     * @return Total de ventas realizdas y total de ingresos recibidos.
     */
    @Query("""
            SELECT
                COUNT(v.id) as totalVentas,
                SUM(v.total) as totalIngresos
            FROM Venta v
            WHERE v.estado = 'PAGADA'
            """)
    ResumenVentasProjection obtenerResumenVentas();
}
