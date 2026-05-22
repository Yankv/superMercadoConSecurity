package com.yank.superMercado.repository;

import java.util.ArrayList;
import java.util.List;

import com.yank.superMercado.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// Esta es una clase para probar distintas formas de implementar consultas con parametros opcionales.
// En esta se hace una implementación propia.
public class ProductoRepositoryCImpl implements ProductoRepositoryC {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> buscarConFiltros(String nombre, String categoria, Double precioMin, Double precioMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Producto> query = cb.createQuery(Producto.class);

        Root<Producto> root = query.from(Producto.class);

        List<Predicate> pridicates = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            pridicates.add(
                    cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (categoria != null && !categoria.isBlank()) {
            pridicates.add(
                    cb.like(cb.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%"));
        }

        if (precioMin != null) {
            pridicates.add(cb.lessThanOrEqualTo(root.get("precio"), precioMin));
        }

        if (precioMax != null) {
            pridicates.add(cb.greaterThanOrEqualTo(root.get("precio"), precioMax));
        }

        query.select(root).where(cb.and(pridicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

}
