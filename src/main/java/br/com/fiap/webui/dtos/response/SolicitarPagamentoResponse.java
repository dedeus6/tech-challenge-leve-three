package br.com.fiap.webui.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.IDENTIFICADOR_PAGAMENTO;
import static br.com.fiap.webui.description.Descriptions.QRCODE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarPagamentoResponse {

    @Schema(description = QRCODE)
    private String qrcode;
    @Schema(description = IDENTIFICADOR_PAGAMENTO)
    private String identificadorPagamento;
}