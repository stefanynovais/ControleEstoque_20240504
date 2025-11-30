//como no endpoint de venda não enviamos a entidade "Venda" inteira, criamos um DTO para "formatar" a requisição no JSON

package com.controleestoque.api_estoque.dto;

import java.util.List;
import lombok.Data;

@Data
public class VendaDTO {
    private Long clienteId;
    private List<VendaItemDTO> itens;
}
