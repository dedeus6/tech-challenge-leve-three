package br.com.fiap.challenge.diner.adapter.driver.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.FORMA_PAGAMENTO_ID;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.PEDIDO_ID;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.CAMPO_REQUERIDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitarPagamentoRequest {

    @Schema(description = FORMA_PAGAMENTO_ID)
    @NotNull(message = CAMPO_REQUERIDO)
    private Long formaPagamentoId;
    @Schema(description = PEDIDO_ID)
    @NotNull(message = CAMPO_REQUERIDO)
    private Long pedidoId;
}
