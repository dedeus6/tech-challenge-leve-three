package br.com.fiap.app.integration.mercadopago.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemQRCodeRequest {

    @JsonProperty("title")
    private String descricaoItem;

    @JsonProperty("unit_price")
    private Double precoUnitario;

    @JsonProperty("quantity")
    private Long quantidade;

    @JsonProperty("unit_measure")
    private String descricaoUnidade;

    @JsonProperty("total_amount")
    private Double valorTotal;
}