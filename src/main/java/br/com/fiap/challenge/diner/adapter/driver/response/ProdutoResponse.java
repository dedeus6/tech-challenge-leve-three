package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.CATEGORIA_PRODUTO;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.DESCRICAO_PRODUTO;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ID;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.IMAGEM_PRODUTO;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.VLR_UNITARIO_PRODUTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProdutoResponse {

    @Schema(description = ID)
    private Integer id;
    @Schema(description = DESCRICAO_PRODUTO)
    private String descricao;
    @Schema(description = VLR_UNITARIO_PRODUTO)
    private BigDecimal vlrUnitario;
    @Schema(description = CATEGORIA_PRODUTO)
    private CategoriaResponse categoria;
    @Schema(description = IMAGEM_PRODUTO)
    private String imagem;
}
