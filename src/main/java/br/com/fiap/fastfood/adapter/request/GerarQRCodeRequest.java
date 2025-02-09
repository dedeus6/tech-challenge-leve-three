package br.com.fiap.fastfood.adapter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerarQRCodeRequest {

    @JsonProperty("external_reference")
    private String referenciaExterna;

    @JsonProperty("title")
    private String titulo;

    @JsonProperty("description")
    private String descricao;

    @JsonProperty("total_amount")
    private Double vlrTotal = 0.0;

    @JsonProperty("items")
    private List<ItemQRCodeRequest> itens = new ArrayList<>();

}
