package br.com.fiap.challenge.diner.core.domain.entities;

import br.com.fiap.challenge.diner.adapter.driver.exception.BusinessException;
import br.com.fiap.challenge.diner.core.application.utils.Numbers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.fiap.challenge.diner.core.application.errors.Errors.STATUS_INVALIDO;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Builder.Default
    @Column(name = "vlr_total")
    private Double vlrTotal = 0.0;

    private String status;

    @OrderBy("id")
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoPagamento> pagamentos = new ArrayList<>();

    @Builder.Default
    @OrderBy("id")
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoItem> itens = new ArrayList<>();

    private String observacao;

    public PedidoItem addItem() {
        PedidoItem item = new PedidoItem();
        item.setPedido(this);
        itens.add(item);
        return item;
    }

    public PedidoPagamento addPagamento() {
        PedidoPagamento item = new PedidoPagamento();
        item.setPedido(this);
        pagamentos.add(item);
        return item;
    }

    public void calculaVlrTotal() {
        var valor = BigDecimal.valueOf(this.itens.stream()
                .mapToDouble(item -> item.getVlrUnitario() * item.getQtdProduto())
                .sum()).setScale(2, RoundingMode.HALF_UP);

        this.vlrTotal = Numbers.parseDouble(valor, 0.0);
    }

    public String getStatusStr() {
        return switch (this.status) {
            case "R" -> "RECEBIDO";
            case "E" -> "EM PREPARAÇÃO";
            case "P" -> "PRONTO";
            case "F" -> "FINALIZADO";
            default -> "INVÁLIDO";
        };
    }

    public String getNextStatus() {
        return switch (this.status) {
            case "E" -> "P";
            case "P" -> "F";
            default -> throw new BusinessException(STATUS_INVALIDO, UNPROCESSABLE_ENTITY);
        };
    }

    @Override
    public String toString() {
        return "Id " + id;
    }
}
