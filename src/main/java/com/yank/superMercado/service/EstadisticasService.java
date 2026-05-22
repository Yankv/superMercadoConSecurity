package com.yank.superMercado.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yank.superMercado.dto.response.ProductoTopVentasDto;
import com.yank.superMercado.dto.response.ResumenVentas;
import com.yank.superMercado.dto.response.SucursalTopVentasDto;
import com.yank.superMercado.exception.NotFoundException;
import com.yank.superMercado.mapper.ProductoMapper;
import com.yank.superMercado.mapper.SucursalMapper;
import com.yank.superMercado.mapper.VentaMapper;
import com.yank.superMercado.repository.ProductoRepository;
import com.yank.superMercado.repository.SucursalRepository;
import com.yank.superMercado.repository.VentaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstadisticasService implements IEstadisticasService {
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public SucursalTopVentasDto obtenerSucursalConMasVentas() {
        return obtenerTopNSucursalesConMasVentas(1)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No hay ventas registradas"));
    }

    @Override
    public List<SucursalTopVentasDto> obtenerTopNSucursalesConMasVentas(int limite) {
        if (limite < 1)
            throw new IllegalArgumentException("El límite debe ser mayor a 0");

        return sucursalMapper.toTopVentasDtoList(
                sucursalRepository.findTopSucursalesConMasVentas(limite));
    }

    @Override
    public ProductoTopVentasDto obtenerProductoMasVendido() {
        return obtenerTopProductosMasVendidos(1)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No hay ventas registradas"));
    }

    @Override
    public List<ProductoTopVentasDto> obtenerTopProductosMasVendidos(int limite) {
        if (limite < 1)
            throw new IllegalArgumentException("El límite debe ser mayor a 0");

        return productoMapper.topVentasDtosList(productoRepository.findTopProductosMasVendidos(limite));
    }

    @Override
    public ResumenVentas obtenerResumenVentas() {
        return ventaMapper.toResumenVentas(ventaRepository.obtenerResumenVentas());
    }
}
