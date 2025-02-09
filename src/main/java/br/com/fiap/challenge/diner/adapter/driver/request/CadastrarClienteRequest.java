package br.com.fiap.challenge.diner.adapter.driver.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.EMAIL_CLIENTE;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.NOME_CLIENTE;
import static br.com.fiap.challenge.diner.core.application.description.Descriptions.TELEFONE_CLIENTE;
import static br.com.fiap.challenge.diner.core.application.errors.Errors.CAMPO_REQUERIDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarClienteRequest {

    @Schema(description = NOME_CLIENTE)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String nome;
    @Schema(description = NOME_CLIENTE)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String cpf;
    @Schema(description = TELEFONE_CLIENTE)
    private String telefone;
    @Schema(description = EMAIL_CLIENTE)
    private String email;
}
