package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ATIVO;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.CNPJ;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.ID;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.NOME_FANTANSIA;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.RAZAO_SOCIAL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EmpresaResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = RAZAO_SOCIAL)
    private String razaoSocial;
    @Schema(description = NOME_FANTANSIA)
    private String nomeFantasia;
    @Schema(description = CNPJ)
    private String cnpj;
    @Schema(description = ATIVO)
    private String ativo;
}
