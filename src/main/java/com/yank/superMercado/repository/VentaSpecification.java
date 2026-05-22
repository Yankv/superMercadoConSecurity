package com.yank.superMercado.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.yank.superMercado.model.Venta;

// Esta es una clase para probar distintas formas de implementar consultas con parametros opcionales.
// En esta se usan Specification
public class VentaSpecification {
    private VentaSpecification() {
    }

    public static Specification<Venta> porFecha(LocalDate fecha) {
        return (root, query, cb) -> fecha == null ? null : cb.equal(root.get("fecha"), fecha);
    }

    public static Specification<Venta> porSucursal(Long sucursalId) {
        return (root, query, cb) -> sucursalId == null ? null : cb.equal(root.get("sucursal").get("id"), sucursalId);
    }

    public static Specification<Venta> estadoNoEsCancelada() {
        return (root, query, cb) -> cb.notEqual(root.get("estado"), "CANCELADA");
    }
}
