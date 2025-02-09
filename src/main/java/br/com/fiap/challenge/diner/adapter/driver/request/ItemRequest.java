package br.com.fiap.challenge.diner.adapter.driver.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ID_PRODUTO;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.QTD_PRODUTO;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.CAMPO_REQUERIDO;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.VALOR_MAIOR_QUE_0;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @Schema(description = ID_PRODUTO)
    @NotNull(message = CAMPO_REQUERIDO)
    private Long produtoId;

    @Schema(description = QTD_PRODUTO)
    @NotNull(message = CAMPO_REQUERIDO)
    @Min(value = 1, message = VALOR_MAIOR_QUE_0)
    private Long qtdProduto;

}
