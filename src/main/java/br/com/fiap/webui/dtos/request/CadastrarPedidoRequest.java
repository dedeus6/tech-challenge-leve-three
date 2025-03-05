package br.com.fiap.webui.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static br.com.fiap.webui.description.Descriptions.*;
import static br.com.fiap.errors.Errors.CAMPO_REQUERIDO;
import static br.com.fiap.errors.Errors.LISTA_ITENS_DEVE_TER_UM_ELEMENTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarPedidoRequest {

    @Schema(description = ID_CLIENTE)
    private Long clienteId;

    @Schema(description = ID_EMPRESA)
    @NotNull(message = CAMPO_REQUERIDO)
    private Long empresaId;

    @Valid
    @Schema(description = LISTA_ITENS_PEDIDO)
    @NotNull(message = CAMPO_REQUERIDO)
    @Size(min = 1, message = LISTA_ITENS_DEVE_TER_UM_ELEMENTO)
    private List<ItemRequest> itens;

    @Schema(description = OBSERVACAO)
    private String observacao;
}
