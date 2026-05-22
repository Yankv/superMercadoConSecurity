package com.yank.superMercado.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yank.superMercado.dto.request.ProductoRequest;
import com.yank.superMercado.dto.response.ProductoResponse;
import com.yank.superMercado.service.IProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final IProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest productoDto) {
        ProductoResponse producto = productoService.crearProducto(productoDto);
        return ResponseEntity.created(URI.create("/api/productos/" + producto.getId()))
                .body(producto);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerProductos() {
        List<ProductoResponse> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(@PathVariable Long id) {
        ProductoResponse producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@PathVariable Long id,
            @Valid @RequestBody ProductoRequest productoDto) {
        ProductoResponse producto = productoService.actualizarProducto(id, productoDto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosConFiltros(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria, @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        List<ProductoResponse> productos = productoService.obtenerProductosConFiltros(nombre, categoria, precioMin,
                precioMax);
        return ResponseEntity.ok(productos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        boolean response = productoService.actualizarStock(id, stock);
        if (response) {
            return ResponseEntity.ok("Stock actualizado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
