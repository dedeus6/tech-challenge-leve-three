package br.com.fiap.webui.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {

    private Long formaPagamentoId;
    private BigDecimal vlrPagamento;

}
