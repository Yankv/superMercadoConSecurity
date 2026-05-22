package com.yank.superMercado.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenVentas {
    private int totalVentas;

    private Double totalIngresos;
}
