package br.com.fiap.challenge.diner.adapter.driver.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.fiap.challenge.diner.core.application.description.Descriptions.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PedidoResponse {

    @Schema(description = ID)
    private Long id;

    @Schema(description = DATA_PEDIDO)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime data;

    @Schema(description = BLOCO_EMPRESA)
    private EmpresaResponse empresa;

    @Schema(description = BLOCO_CLIENTE)
    private ClienteResponse cliente;

    @Schema(description = VLR_TOTAL)
    private Double vlrTotal;

    @Schema(description = STATUS_PEDIDO)
    private String status;

    @Schema(description = LISTA_ITENS_PEDIDO)
    private List<PedidoItemResponse> itens;

    //TODO: ajustar
    private List<PedidoPagamentoResponse> pagamentos;

    @Schema(description = OBSERVACAO)
    private String observacao;

}
