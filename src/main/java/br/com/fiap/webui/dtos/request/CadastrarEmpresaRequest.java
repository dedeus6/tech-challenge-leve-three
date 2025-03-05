package br.com.fiap.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.CNPJ;
import static br.com.fiap.webui.description.Descriptions.NOME_FANTANSIA;
import static br.com.fiap.webui.description.Descriptions.RAZAO_SOCIAL;
import static br.com.fiap.errors.Errors.CAMPO_REQUERIDO;
import static br.com.fiap.errors.Errors.CNPJ_INVALIDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarEmpresaRequest {

    @Schema(description = RAZAO_SOCIAL)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String razaoSocial;
    @Schema(description = NOME_FANTANSIA)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String nomeFantasia;
    @Schema(description = CNPJ)
    @NotBlank(message = CAMPO_REQUERIDO)
    @Size(min = 14, max = 14, message = CNPJ_INVALIDO)
    private String cnpj;
}
