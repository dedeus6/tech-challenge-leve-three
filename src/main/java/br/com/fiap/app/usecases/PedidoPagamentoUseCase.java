package br.com.fiap.app.usecases;

import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.webui.dtos.response.SolicitarPagamentoResponse;
import br.com.fiap.app.repositories.FormaPagamentoRepository;
import br.com.fiap.app.repositories.PedidoPagamentoRepository;
import br.com.fiap.app.repositories.PedidoRepository;
import br.com.fiap.utils.Numbers;
import br.com.fiap.utils.Strings;
import br.com.fiap.webui.dtos.SolicitarPagamentoDTO;
import br.com.fiap.webui.dtos.WebhookPagamentoDTO;
import br.com.fiap.app.entities.FormaPagamento;
import br.com.fiap.app.entities.Pedido;
import br.com.fiap.app.entities.PedidoPagamento;
import br.com.fiap.enums.StatusPedidoPagamento;
import br.com.fiap.enums.TipoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.fiap.errors.Errors.*;
import static br.com.fiap.utils.DateUtils.formataData;
import static br.com.fiap.enums.StatusPedido.EM_PREPARACAO;
import static br.com.fiap.enums.StatusPedido.RECEBIDO;
import static br.com.fiap.enums.StatusPedidoPagamento.CONFIRMADO;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class PedidoPagamentoUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoPagamentoRepository pedidoPagamentoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final MercadoPagoUseCase mercadoPagoUseCase;

    public SolicitarPagamentoResponse solicitarPagamento(SolicitarPagamentoDTO requestDTO) {
        var pedido = this.getPedido(requestDTO.getPedidoId());

        if (Numbers.isEmpty(pedido.getVlrTotal()))
            throw new BusinessException(PEDIDO_VALOR_TOTAL_ZERO, UNPROCESSABLE_ENTITY);
        if (Strings.diff(pedido.getStatus(), RECEBIDO.getValor()))
            throw new BusinessException(PEDIDO_STATUS_DIFERENTE_RECEBIDO, UNPROCESSABLE_ENTITY);

        var formaPagamento = this.getFormaPagamento(requestDTO.getFormaPagamentoId());
        var solicitacaoPagamento = solicitarPagamento(formaPagamento.getTipoPagamento(), pedido);
        pedidoPagamentoRepository.save(getPedidoPagamento(formaPagamento, pedido, solicitacaoPagamento));
        return solicitacaoPagamento;
    }

    public void webhookPagamento(WebhookPagamentoDTO webhookDTO) {
        var pedidoPagamento = getPedidoPagamento(webhookDTO.getData().getId());

        pedidoPagamento.getPedido().setStatus(EM_PREPARACAO.getValor());
        pedidoPagamento.setStatus(CONFIRMADO.getValor());

        if (nonNull(webhookDTO.getCreateDate()))
            pedidoPagamento.setDataNotificacao(formataData(webhookDTO.getCreateDate()));

        pedidoPagamentoRepository.save(pedidoPagamento);
    }

    private static PedidoPagamento getPedidoPagamento(FormaPagamento formaPagamento, Pedido pedido, SolicitarPagamentoResponse solicitacaoPagamento) {
        return PedidoPagamento.builder()
                .formaPagamento(formaPagamento)
                .pedido(pedido)
                .vlrPagamento(pedido.getVlrTotal())
                .status(StatusPedidoPagamento.PENDENTE.getValor())
                .identificadorPagamento(solicitacaoPagamento.getIdentificadorPagamento())
                .build();
    }

    private SolicitarPagamentoResponse solicitarPagamento(String tipoPagamento, Pedido pedido) {
        var value = TipoPagamento.valueOf(tipoPagamento);
        return switch (value) {
            case TipoPagamento.PIX -> mercadoPagoUseCase.solicitarPagamentoPix(pedido);
            default -> throw new BusinessException(FORMA_PAGAMENTO_NAO_DISPONIVEL, UNPROCESSABLE_ENTITY);
        };
    }

    private Pedido getPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new BusinessException(PEDIDO_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

    private FormaPagamento getFormaPagamento(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new BusinessException(FORMA_PAGAMENTO_NAO_ENCONTRADO, UNPROCESSABLE_ENTITY));
    }

    private PedidoPagamento getPedidoPagamento(String pagamentoId) {
        return pedidoPagamentoRepository.findByIdentificadorPagamento(pagamentoId)
                .orElseThrow(() -> new BusinessException(PEDIDO_PAGAMENTO_INVALIDO, UNPROCESSABLE_ENTITY));
    }

}
