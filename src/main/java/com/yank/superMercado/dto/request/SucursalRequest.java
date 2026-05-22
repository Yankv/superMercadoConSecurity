package com.yank.superMercado.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {
    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La dirección de la sucursal no puede estar vacía")
    private String direccion;
}
