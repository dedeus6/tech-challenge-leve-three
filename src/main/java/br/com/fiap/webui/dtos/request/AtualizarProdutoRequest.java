package br.com.fiap.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.fiap.webui.description.Descriptions.*;
import static br.com.fiap.errors.Errors.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarProdutoRequest {

    @Schema(description = DESCRICAO_PRODUTO)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String descricao;

    @Schema(description = VLR_UNITARIO_PRODUTO)
    @NotNull(message = CAMPO_REQUERIDO)
    @Digits(integer = 10, fraction = 2, message = VLR_UNITARIO_FORMATO_INVALIDO)
    private BigDecimal vlrUnitario;

    @Schema(description = CATEGORIA_ID)
    @NotNull(message = CAMPO_REQUERIDO)
    private Long categoriaId;

}
