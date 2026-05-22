package com.yank.superMercado.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yank.superMercado.dto.response.ProductoTopVentasDto;
import com.yank.superMercado.dto.response.ResumenVentas;
import com.yank.superMercado.dto.response.SucursalTopVentasDto;
import com.yank.superMercado.service.IEstadisticasService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
public class EstadisticasController {
    private final IEstadisticasService estadisticasService;

    @GetMapping("/sucursal-mas-ventas")
    public ResponseEntity<SucursalTopVentasDto> obtenerSucursalConMasVentas() {
        SucursalTopVentasDto sucursal = estadisticasService.obtenerSucursalConMasVentas();
        return ResponseEntity.ok(sucursal);
    }

    @GetMapping("/top-sucursales-mas-ventas")
    public ResponseEntity<List<SucursalTopVentasDto>> obtenerTopSucursalConMasVentas(
            @RequestParam(defaultValue = "10") int limite) {
        List<SucursalTopVentasDto> sucursales = estadisticasService.obtenerTopNSucursalesConMasVentas(limite);
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoTopVentasDto> obtenerProductoMasVendido() {
        ProductoTopVentasDto producto = estadisticasService.obtenerProductoMasVendido();
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/top-productos-mas-vendidos")
    public ResponseEntity<List<ProductoTopVentasDto>> obtenerTopProductosMasVendidos(
            @RequestParam(defaultValue = "10") int limite) {
        List<ProductoTopVentasDto> productos = estadisticasService.obtenerTopProductosMasVendidos(limite);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/resumen-ventas")
    public ResponseEntity<ResumenVentas> obtenerResumenVentas() {
        ResumenVentas resumen = estadisticasService.obtenerResumenVentas();
        return ResponseEntity.ok(resumen);
    }
}