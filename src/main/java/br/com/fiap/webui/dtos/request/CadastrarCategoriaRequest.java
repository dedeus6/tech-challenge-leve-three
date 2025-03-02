package br.com.fiap.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.DESCRICAO_CATEGORIA;
import static br.com.fiap.errors.Errors.CAMPO_REQUERIDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarCategoriaRequest {

    @Schema(description = DESCRICAO_CATEGORIA)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String descricao;

}
