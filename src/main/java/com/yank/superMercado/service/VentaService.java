package com.yank.superMercado.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yank.superMercado.dto.request.ActualizarVentaRequest;
import com.yank.superMercado.dto.request.CrearVentaRequest;
import com.yank.superMercado.dto.response.VentaResponse;
import com.yank.superMercado.enums.EstadoVenta;
import com.yank.superMercado.exception.InsufficientStockException;
import com.yank.superMercado.exception.NotFoundException;
import com.yank.superMercado.mapper.DetalleVentaMapper;
import com.yank.superMercado.mapper.VentaMapper;
import com.yank.superMercado.model.DetalleVenta;
import com.yank.superMercado.model.Producto;
import com.yank.superMercado.model.Venta;
import com.yank.superMercado.repository.ProductoRepository;
import com.yank.superMercado.repository.SucursalRepository;
import com.yank.superMercado.repository.VentaRepository;
import com.yank.superMercado.repository.VentaSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService implements IVentaService {
    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    public VentaResponse crearVenta(CrearVentaRequest ventaDto) {
        // Mapear el DTO a la entidad
        var venta = ventaMapper.toEntity(ventaDto);
        // Obtener la sucursal por su ID y asignarla a la venta
        var sucursal = sucursalRepository.findById(ventaDto.getIdSucursal())
                .orElseThrow(
                        () -> new NotFoundException("No se encontró la sucursal con ID: " + ventaDto.getIdSucursal()));
        venta.setSucursal(sucursal);

        if (ventaDto.getDetalles() != null) {
            // Mapear los detalles de venta del DTO a la entidad
            var detalles = ventaDto.getDetalles().stream()
                    .map(detalleDto -> {
                        DetalleVenta detalle = detalleVentaMapper.toEntity(detalleDto);

                        // Buscar el producto por su ID
                        Producto producto = productoRepository.findById(detalleDto.getProductoId())
                                .orElseThrow(() -> new NotFoundException(
                                        "No se encontró el producto con ID: " + detalleDto.getProductoId()));

                        // Validar que el producto tenga stock suficiente
                        var nuevoStock = producto.getStock() - detalle.getCantidad();
                        if (nuevoStock < 0) {
                            throw new InsufficientStockException(
                                    "El producto " + producto.getId() + " no tiene stock suficiente.");
                        }

                        // Se actualiza el stock del producto
                        producto.setStock(nuevoStock);

                        detalle.setProducto(producto); // Asignar el producto al detalle
                        detalle.setVenta(venta); // Asignar la venta al detalle
                        return detalle;
                    })
                    .toList();

            // Asignar la lista de detalles a la venta
            venta.setDetalles(detalles);
        }
        // Guardar la venta en la base de datos y retornar el DTO resultante
        return ventaMapper.toDto(ventaRepository.save(venta));
    }

    @Override
    public List<VentaResponse> obtenerVentas() {
        // Obtener todas las ventas de la base de datos, mapearlas a DTOs y retornarlas
        List<Venta> ventas = ventaRepository.findAll();

        if (ventas.isEmpty()) {
            throw new NotFoundException("No se encontraron ventas registradas.");
        }

        return ventaMapper.toDtoList(ventas);
    }

    @Override
    public VentaResponse obtenerVentaPorId(Long id) {
        // Obtener la venta por su ID, mapearla a un DTO y retornarla
        var venta = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la venta con ID: " + id));
        return ventaMapper.toDto(venta);
    }

    @Override
    public List<VentaResponse> obtenerVentasPorSucursalYFecha(Long sucursalId, LocalDate fecha) {
        // var ventas = ventaRepository.buscarPorSucursalIdYFecha(sucursalId, fecha);
        Specification<Venta> spec = Specification
                .where(VentaSpecification.estadoNoEsCancelada())
                .and(VentaSpecification.porSucursal(sucursalId))
                .and(VentaSpecification.porFecha(fecha));
        
        List<Venta> ventas = ventaRepository.findAll(spec);

        if (ventas.isEmpty()) {
            throw new NotFoundException(
                    "No se encontraron ventas para la sucursal con ID: " + sucursalId + " en la fecha: " + fecha);
        }
        return ventaMapper.toDtoList(ventas);
    }

    @Override
    public VentaResponse actualizarVenta(Long id, ActualizarVentaRequest ventaDto) {
        // Verificar si existe la venta a actualizar
        var ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la venta con ID: " + id));

        if (ventaDto.getIdSucursal() != null) {
            // Obtener la sucursal por su ID y asignarla a la venta
            var sucursal = sucursalRepository.findById(ventaDto.getIdSucursal())
                    .orElseThrow(
                            () -> new NotFoundException(
                                    "No se encontró la sucursal con ID: " + ventaDto.getIdSucursal()));
            ventaExistente.setSucursal(sucursal);
        }

        // Mapear los campos actualizables del DTO a la entidad existente
        ventaExistente.setFecha(ventaDto.getFecha());
        ventaExistente.setEstado(ventaDto.getEstado());

        // Guardar la venta actualizada en la base de datos y retornar el DTO resultante
        return ventaMapper.toDto(ventaRepository.save(ventaExistente));
    }

    @Override
    public void eliminarVenta(Long id) {
        // Verificar si existe la venta a eliminar
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la venta con ID: " + id));

        // Si existe, cambiar el estado
        venta.setEstado(EstadoVenta.CANCELADA);
        ventaRepository.save(venta);
    }

}
