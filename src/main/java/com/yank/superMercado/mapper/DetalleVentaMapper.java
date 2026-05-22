package com.yank.superMercado.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.yank.superMercado.dto.request.DetalleVentaRequest;
import com.yank.superMercado.dto.response.DetalleVentaResponse;
import com.yank.superMercado.model.DetalleVenta;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {
    /**
     * Convierte un objeto DetalleVentaRequest en una entidad DetalleVenta.
     * Los campos como producto, venta e id se ignoran ya que se manejan por separado
     * en el proceso de creación o actualización.
     * 
     * @param dto El objeto DetalleVentaRequest que contiene los datos de entrada.
     * @return La entidad DetalleVenta resultante de la conversión.
     */
    // Se ignoran los campos que no existen en el DTO
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "id", ignore = true)
    DetalleVenta toEntity(DetalleVentaRequest dto);

    /**
     * Convierte una entidad DetalleVenta en un objeto DetalleVentaResponse.
     * Mapea el nombre del producto al campo correspondiente y calcula el subtotal
     * posteriormente mediante un método auxiliar.
     * 
     * @param entity La entidad DetalleVenta que se va a convertir.
     * @return El objeto DetalleVentaResponse resultante.
     */
    @Mapping(source = "producto.nombre", target = "nombreProducto") // Mapea el nombre del producto al campo nombreProducto del DTO
    @Mapping(target = "subTotal", ignore = true) // Ignora el campo subtotal, se calculará en el servicio
    DetalleVentaResponse toDto(DetalleVenta entity);

    /**
     * Convierte una lista de entidades DetalleVenta en una lista de objetos DetalleVentaResponse.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param entities La lista de entidades DetalleVenta a convertir.
     * @return La lista de objetos DetalleVentaResponse resultantes.
     */
    List<DetalleVentaResponse> toDtoList(List<DetalleVenta> entities);

    /**
     * Calcula el subtotal del detalle de venta multiplicando la cantidad por el precio unitario
     * y lo asigna al objeto DetalleVentaResponse después del mapeo principal.
     * 
     * @param detalle La entidad DetalleVenta con los datos necesarios para el cálculo.
     * @param dto El objeto DetalleVentaResponse al que se asignará el subtotal.
     */
    @AfterMapping
    default void calcularSubtotal(DetalleVenta detalle, @MappingTarget DetalleVentaResponse dto) {
        if (detalle.getProducto() != null) {
            dto.setSubTotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        }
    }
}
