package br.com.fiap.challenge.diner.adapter.driver.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPagamentoResponse {

    @Schema(description = FORMA_PAGAMENTO_ID)
    private Long id;

    //TODO: ajustar
    private FormaPagamentoResponse formaPagamento;

    @Schema(description = VLR_PAGAMENTO)
    private Double vlrPagamento;

    @Schema(description = IDENTIFICADOR_PAGAMENTO)
    private String identificadorPagamento;

    @Schema(description = STATUS_PAGAMENTO)
    private String status;

}
