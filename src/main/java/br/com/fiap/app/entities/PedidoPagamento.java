package br.com.fiap.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido_pagamento")
public class PedidoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "vlr_pagamento")
    private Double vlrPagamento = 0.0;

    @Column(name = "identificador_pagamento")
    private String identificadorPagamento;

    private String status;

    @Column(name = "data_notificacao")
    private String dataNotificacao;

    public String getStatusStr() {
        return switch (this.getStatus()) {
            case "P" -> "PEDENTE";
            case "C" -> "CONFIRMADO";
            case "R" -> "RECUSADO";
            default -> "INVÁLIDO";
        };
    }

    @Override
    public String toString() {
        return "Id " + id;
    }
}
