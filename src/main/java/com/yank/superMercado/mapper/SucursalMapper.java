package com.yank.superMercado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.yank.superMercado.dto.request.SucursalRequest;
import com.yank.superMercado.dto.response.SucursalResponse;
import com.yank.superMercado.dto.response.SucursalTopVentasDto;
import com.yank.superMercado.model.Sucursal;
import com.yank.superMercado.projection.SucursalTopVentasProjection;

@Mapper(componentModel = "spring")
public interface SucursalMapper {
    /**
     * Convierte un objeto SucursalRequest en una entidad Sucursal.
     * Los campos ventas e id se ignoran ya que se manejan por separado
     * en el proceso de creación o actualización.
     * 
     * @param dto El objeto SucursalRequest que contiene los datos de entrada.
     * @return La entidad Sucursal resultante de la conversión.
     */
    @Mapping(target = "ventas", ignore = true)
    @Mapping(target = "id", ignore = true)
    Sucursal toEntity(SucursalRequest dto);

    /**
     * Convierte una entidad Sucursal en un objeto SucursalResponse.
     * Realiza un mapeo directo de los campos correspondientes.
     * 
     * @param entity La entidad Sucursal que se va a convertir.
     * @return El objeto SucursalResponse resultante.
     */
    SucursalResponse toDto(Sucursal entity);

    /**
     * Convierte una lista de entidades Sucursal en una lista de objetos
     * SucursalResponse.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param entities La lista de entidades Sucursal a convertir.
     * @return La lista de objetos SucursalResponse resultantes.
     */
    List<SucursalResponse> toDtoList(List<Sucursal> entities);

    /**
     * Convierte una proyección SucursalTopVentasProjection en un DTO
     * SucursalTopVentasDto.
     * 
     * @param projection La proyección de sucursal con información de ventas.
     * @return El DTO correspondiente con la información mapeada.
     */
    @Mapping(source = "idSucursal", target = "idSucursal")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "totalVentas", target = "totalVentas")
    SucursalTopVentasDto toTopVentasDto(SucursalTopVentasProjection projection);

    /**
     * Convierte una lista de proyecciones SucursalTopVentasProjection en una lista
     * de DTOs SucursalTopVentasDto.
     * Aplica la conversión individual a cada elemento de la lista.
     * 
     * @param projections La lista de proyecciones a convertir.
     * @return La lista de DTOs resultantes.
     */
    List<SucursalTopVentasDto> toTopVentasDtoList(List<SucursalTopVentasProjection> projections);
}
