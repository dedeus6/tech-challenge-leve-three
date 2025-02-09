package br.com.fiap.fastfood.adapter.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GerarQRCodeResponse {

    @JsonProperty("in_store_order_id")
    private String idPedidoLojaExterna;
    @JsonProperty("qr_data")
    private String qrcode;
}
