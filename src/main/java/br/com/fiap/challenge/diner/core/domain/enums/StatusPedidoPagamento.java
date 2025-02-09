package br.com.fiap.challenge.diner.core.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedidoPagamento {

    PENDENTE("P"),
    CONFIRMADO("C"),
    RECUSADO("R");

    private String valor;
}
