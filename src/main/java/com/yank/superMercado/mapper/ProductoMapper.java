package com.yank.superMercado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yank.superMercado.dto.request.ProductoRequest;
import com.yank.superMercado.dto.response.ProductoResponse;
import com.yank.superMercado.dto.response.ProductoTopVentasDto;
import com.yank.superMercado.model.Producto;
import com.yank.superMercado.projection.ProductoTopVentasProjection;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    /**
     * Convierte un objeto ProductoRequest en una entidad Producto.
     * Los campos detallesVenta e id se ignoran ya que se manejan por separado
     * en el proceso de creación o actualización.
     * 
     * @param dto El objeto ProductoRequest que contiene los datos de entrada.
     * @return La entidad Producto resultante de la conversión.
     */
    // Se ignoran los campos que no existen en el DTO
    @Mapping(target = "detallesVenta", ignore = true)
    @Mapping(target = "id", ignore = true)
    Producto toEntity(ProductoRequest dto);

    /**
     * Convierte una entidad Producto en un objeto ProductoResponse.
     * Realiza un mapeo directo de los campos correspondientes.
     * 
     * @param entity La entidad Producto que se va a convertir.
     * @return El objeto ProductoResponse resultante.
     */
    ProductoResponse toDto(Producto entity);

    /**
     * Convierte una lista de entidades Producto en una lista de objetos
     * ProductoResponse.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param entities La lista de entidades Producto a convertir.
     * @return La lista de objetos ProductoResponse resultantes.
     */
    List<ProductoResponse> toDtoList(List<Producto> entities);

    /**
     * Convierte una proyección ProductoTopVentasProjection en un DTO
     * ProductoTopVentasDto.
     * Mapea los campos idSucursal, nombre, direccion y totalVentas de la proyección
     * al DTO.
     * 
     * @param projections La proyección de sucursal con información de ventas.
     * @return El DTO correspondiente con la información mapeada.
     */
    @Mapping(source = "idProducto", target = "idProducto")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "precio", target = "precio")
    @Mapping(source = "totalUnidadesVendidas", target = "totalUnidadesVendidas")
    ProductoTopVentasDto toTopVentasDto(ProductoTopVentasProjection projection);

    /**
     * Convierte una lista de proyecciones ProductoTopVentasProjection en una lista
     * de DTOs ProductoTopVentasDto.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param projections La lista de proyecciones a convertir.
     * @return La lista de DTOs resultantes.
     */
    List<ProductoTopVentasDto> topVentasDtosList(List<ProductoTopVentasProjection> projections);
}
