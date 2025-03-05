package br.com.fiap.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.EMAIL_CLIENTE;
import static br.com.fiap.webui.description.Descriptions.NOME_CLIENTE;
import static br.com.fiap.webui.description.Descriptions.TELEFONE_CLIENTE;
import static br.com.fiap.errors.Errors.CAMPO_REQUERIDO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarClienteRequest {

    @Schema(description = NOME_CLIENTE)
    @NotBlank(message = CAMPO_REQUERIDO)
    private String nome;
    @Schema(description = TELEFONE_CLIENTE)
    private String telefone;
    @Schema(description = EMAIL_CLIENTE)
    private String email;
}
