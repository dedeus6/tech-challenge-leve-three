package br.com.fiap.fastfood.adapter.integration;

import br.com.fiap.fastfood.adapter.request.GerarQRCodeRequest;
import br.com.fiap.fastfood.adapter.response.GerarQRCodeResponse;
import br.com.fiap.fastfood.core.configuration.MercadoPagoIntegrationConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "mercado-pago", url = "https://api.mercadopago.com", configuration = MercadoPagoIntegrationConfig.class)
public interface MercadoPagoIntegration {

    @RequestMapping(method = RequestMethod.POST, value = "/instore/orders/qr/seller/collectors/{userId}/pos/{externalPosId}/qrs", consumes = "application/json")
    GerarQRCodeResponse gerarQRCode(@PathVariable("userId") String userId,
                                    @PathVariable("externalPosId") String externalPosId,
                                    GerarQRCodeRequest request);
}
