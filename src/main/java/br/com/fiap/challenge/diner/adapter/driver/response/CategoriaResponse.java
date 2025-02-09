package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.DESCRICAO_CATEGORIA;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CategoriaResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = DESCRICAO_CATEGORIA)
    private String descricao;
}
