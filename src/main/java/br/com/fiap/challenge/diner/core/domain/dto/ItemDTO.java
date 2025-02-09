package br.com.fiap.challenge.diner.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private Long produtoId;
    private Long pedidoId;
    private Long qtdProduto;

}
