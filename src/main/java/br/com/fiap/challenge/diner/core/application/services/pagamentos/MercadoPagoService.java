package br.com.fiap.challenge.diner.core.application.services.pagamentos;

import br.com.fiap.challenge.diner.adapter.driver.response.SolicitarPagamentoResponse;
import br.com.fiap.challenge.diner.core.application.utils.Numbers;
import br.com.fiap.challenge.diner.core.domain.entities.Pedido;
import br.com.fiap.challenge.diner.core.domain.entities.PedidoItem;
import br.com.fiap.challenge.diner.core.domain.enums.TipoUnidade;
import br.com.fiap.fastfood.adapter.integration.MercadoPagoIntegration;
import br.com.fiap.fastfood.adapter.request.GerarQRCodeRequest;
import br.com.fiap.fastfood.adapter.request.ItemQRCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final MercadoPagoIntegration mercadoPagoIntegration;

    @Value("${mercado-pago.user-id}")
    private String userId;
    @Value("${mercado-pago.external-pos-id}")
    private String externalPosId;

    public SolicitarPagamentoResponse solicitarPagamentoPix(Pedido pedido) {
        var response = mercadoPagoIntegration.gerarQRCode(userId, externalPosId, getRequestQRCode(pedido));
        return SolicitarPagamentoResponse.builder()
                .qrcode(response.getQrcode())
                .identificadorPagamento(response.getIdPedidoLojaExterna())
                .build();
    }

    private GerarQRCodeRequest getRequestQRCode(Pedido pedido) {
        List<ItemQRCodeRequest> listaItens = new ArrayList<>();
        for (PedidoItem item : pedido.getItens()) {
            ItemQRCodeRequest itemReq = ItemQRCodeRequest.builder()
                    .descricaoItem(item.getProduto().getDescricao())
                    .precoUnitario(item.getProduto().getVlrUnitario())
                    .quantidade(item.getQtdProduto())
                    .descricaoUnidade(TipoUnidade.UNIT.name())
                    .valorTotal(Numbers.round(item.getQtdProduto() * item.getVlrUnitario(), 2))
                    .build();
            listaItens.add(itemReq);
        }

        return GerarQRCodeRequest.builder()
                .referenciaExterna(String.valueOf(pedido.getId()))
                .titulo(String.format("Pedido %s", pedido.getId()))
                .descricao(String.format("Pedido %s", pedido.getId()))
                .vlrTotal(pedido.getVlrTotal())
                .itens(listaItens)
                .build();
    }
}
