package com.yank.superMercado.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yank.superMercado.dto.request.SucursalRequest;
import com.yank.superMercado.dto.response.SucursalResponse;
import com.yank.superMercado.service.ISucursalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {
    private final ISucursalService sucursalService;

    @PostMapping
    public ResponseEntity<SucursalResponse> crearSucursal(@Valid @RequestBody SucursalRequest sucursalDto) {
        SucursalResponse sucursal = sucursalService.crearSucursal(sucursalDto);
        return ResponseEntity.created(URI.create("/api/" + sucursal.getId()))
                .body(sucursal);
    }

    @GetMapping
    public ResponseEntity<List<SucursalResponse>> obtenerSucursales() {
        return ResponseEntity.ok(sucursalService.obtenerSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerSucursalPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> actualizarSucursal(@PathVariable Long id,
            @Valid @RequestBody SucursalRequest sucursalDto) {
        SucursalResponse sucursalActualizada = sucursalService.actualizarSucursal(id, sucursalDto);
        return ResponseEntity.ok(sucursalActualizada);
    }
}
