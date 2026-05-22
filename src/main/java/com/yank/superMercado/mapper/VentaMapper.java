package com.yank.superMercado.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.yank.superMercado.dto.request.CrearVentaRequest;
import com.yank.superMercado.dto.response.ResumenVentas;
import com.yank.superMercado.dto.response.VentaResponse;
import com.yank.superMercado.model.Venta;
import com.yank.superMercado.projection.ResumenVentasProjection;

@Mapper(componentModel = "spring", uses = { DetalleVentaMapper.class })
public interface VentaMapper {
    /**
     * Convierte un objeto CrearVentaRequest en una entidad Venta.
     * Los campos sucursal e id se ignoran ya que se manejan por separado
     * en el proceso de creación o actualización.
     * 
     * @param dto El objeto CrearVentaRequest que contiene los datos de entrada.
     * @return La entidad Venta resultante de la conversión.
     */
    @Mapping(target = "sucursal", ignore = true)
    @Mapping(target = "id", ignore = true)
    Venta toEntity(CrearVentaRequest dto);

    /**
     * Convierte una entidad Venta en un objeto VentaResponse.
     * Mapea el nombre de la sucursal al campo correspondiente.
     * 
     * @param entity La entidad Venta que se va a convertir.
     * @return El objeto VentaResponse resultante.
     */
    @Mapping(source = "sucursal.nombre", target = "nombreSucursal") // Mapea el ID de la sucursal al campo idSucursal
                                                                    // del DTO
    VentaResponse toDto(Venta entity);

    /**
     * Convierte una lista de entidades Venta en una lista de objetos VentaResponse.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param entities La lista de entidades Venta a convertir.
     * @return La lista de objetos VentaResponse resultantes.
     */
    List<VentaResponse> toDtoList(List<Venta> entities);

    /**
     * Calcula el total de la venta sumando los subtotales de todos los detalles
     * y lo asigna a la entidad Venta después del mapeo principal.
     * 
     * @param dto   El objeto CrearVentaRequest con la lista de detalles.
     * @param venta La entidad Venta a la que se asignará el total.
     */
    // Método para calcular el total después de mapear el DTO a la entidad
    @AfterMapping
    default void calcularTotal(CrearVentaRequest dto, @MappingTarget Venta venta) {
        if (dto.getDetalles() != null) {
            double total = dto.getDetalles().stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                    .sum();
            venta.setTotal(total);
        }
    }

    /**
     * Convierte un proyección ResumenVentasProjection en un DTO ResumenVentas
     * 
     * @param projection
     * @return
     */
    @Mapping(source = "totalVentas", target = "totalVentas")
    @Mapping(source = "totalIngresos", target = "totalIngresos")
    ResumenVentas toResumenVentas(ResumenVentasProjection projection);
}
