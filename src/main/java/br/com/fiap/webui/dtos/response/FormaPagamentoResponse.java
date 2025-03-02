package br.com.fiap.webui.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.fiap.webui.description.Descriptions.DESCRICAO_FORMA_PAGAMENTO;
import static br.com.fiap.webui.description.Descriptions.ID;
import static br.com.fiap.webui.description.Descriptions.TIPO_PAGAMENTO_FORMA_PAGAMENTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FormaPagamentoResponse {

    @Schema(description = ID)
    private Long id;

    @Schema(description = DESCRICAO_FORMA_PAGAMENTO)
    private String descricao;

    @Schema(description = TIPO_PAGAMENTO_FORMA_PAGAMENTO)
    private String tipoPagamento;

}
