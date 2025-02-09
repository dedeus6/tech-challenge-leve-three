package br.com.fiap.challenge.diner.core.application.services;

import br.com.fiap.challenge.diner.adapter.driver.exception.BusinessException;
import br.com.fiap.challenge.diner.adapter.driver.response.SolicitarPagamentoResponse;
import br.com.fiap.challenge.diner.core.application.ports.FormaPagamentoRepository;
import br.com.fiap.challenge.diner.core.application.ports.PedidoPagamentoRepository;
import br.com.fiap.challenge.diner.core.application.ports.PedidoRepository;
import br.com.fiap.challenge.diner.core.application.services.pagamentos.MercadoPagoService;
import br.com.fiap.challenge.diner.core.application.utils.Numbers;
import br.com.fiap.challenge.diner.core.application.utils.Strings;
import br.com.fiap.challenge.diner.core.domain.dto.SolicitarPagamentoDTO;
import br.com.fiap.challenge.diner.core.domain.dto.WebhookPagamentoDTO;
import br.com.fiap.challenge.diner.core.domain.entities.FormaPagamento;
import br.com.fiap.challenge.diner.core.domain.entities.Pedido;
import br.com.fiap.challenge.diner.core.domain.entities.PedidoPagamento;
import br.com.fiap.challenge.diner.core.domain.enums.StatusPedidoPagamento;
import br.com.fiap.challenge.diner.core.domain.enums.TipoPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.fiap.challenge.diner.core.application.errors.Errors.*;
import static br.com.fiap.challenge.diner.core.application.utils.DateUtils.formataData;
import static br.com.fiap.challenge.diner.core.domain.enums.StatusPedido.EM_PREPARACAO;
import static br.com.fiap.challenge.diner.core.domain.enums.StatusPedido.RECEBIDO;
import static br.com.fiap.challenge.diner.core.domain.enums.StatusPedidoPagamento.CONFIRMADO;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class PedidoPagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoPagamentoRepository pedidoPagamentoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final MercadoPagoService mercadoPagoService;

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
            case TipoPagamento.PIX -> mercadoPagoService.solicitarPagamentoPix(pedido);
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
