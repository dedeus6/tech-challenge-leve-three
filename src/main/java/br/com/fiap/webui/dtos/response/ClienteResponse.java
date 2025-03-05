package br.com.fiap.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.CPF_CLIENTE;
import static br.com.fiap.webui.description.Descriptions.EMAIL_CLIENTE;
import static br.com.fiap.webui.description.Descriptions.ID;
import static br.com.fiap.webui.description.Descriptions.NOME_CLIENTE;
import static br.com.fiap.webui.description.Descriptions.TELEFONE_CLIENTE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ClienteResponse {

    @Schema(description = ID)
    private Long id;
    @Schema(description = NOME_CLIENTE)
    private String nome;
    @Schema(description = CPF_CLIENTE)
    private String cpf;
    @Schema(description = TELEFONE_CLIENTE)
    private String telefone;
    @Schema(description = EMAIL_CLIENTE)
    private String email;
}
