package com.yank.superMercado.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yank.superMercado.dto.request.ActualizarVentaRequest;
import com.yank.superMercado.dto.request.CrearVentaRequest;
import com.yank.superMercado.dto.response.VentaResponse;
import com.yank.superMercado.service.IVentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {
    private final IVentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> crearVenta(@Valid @RequestBody CrearVentaRequest ventaDto) {
        VentaResponse venta = ventaService.crearVenta(ventaDto);
        return ResponseEntity.created(URI.create("/api/ventas/" + venta.getId()))
                .body(venta);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> obtenerVentas() {
        List<VentaResponse> ventas = ventaService.obtenerVentas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(id));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<VentaResponse>> obtenerVentasPorSucursalYFecha(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false) LocalDate fecha) {
        List<VentaResponse> ventas = ventaService.obtenerVentasPorSucursalYFecha(sucursalId, fecha);
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> actualizarVenta(@PathVariable Long id,
            @Valid @RequestBody ActualizarVentaRequest ventaDto) {
        return ResponseEntity.ok(ventaService.actualizarVenta(id, ventaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
