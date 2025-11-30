package com.controleestoque.api_estoque.dto;

import lombok.Data;

@Data
public class VendaItemDTO {
    private Long produtoId;
    private int quantidade;
}
