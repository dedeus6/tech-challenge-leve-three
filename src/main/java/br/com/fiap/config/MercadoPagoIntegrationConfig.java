package br.com.fiap.config;

import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.utils.JsonUtils;
import br.com.fiap.utils.Strings;
import br.com.fiap.app.integration.mercadopago.response.ErrorMessageResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static br.com.fiap.config.MapperErrorsMercadoPago.*;

public class MercadoPagoIntegrationConfig implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(MercadoPagoIntegrationConfig.class);

    @Value("${mercado-pago.secret-token}")
    private String token;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Authorization", "Bearer " + token);
    }

    @Override
    public Exception decode(String s, Response response) {

        ErrorMessageResponse message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            message = mapper.readValue(bodyIs, ErrorMessageResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        log.error(String.format("Error: %s", JsonUtils.toJson(message)));
        switch (response.status()) {
            case 400 -> throw new BusinessException(MapperErrorsMercadoPago.getMensagemPtBr(message.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
            default -> throw new BusinessException(String.format(ERRO_GENERICO.getMensagemPtBr(), message.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

@Getter
@NoArgsConstructor
@AllArgsConstructor
enum MapperErrorsMercadoPago {

    ERRO_GENERICO("", "Erro genérico na integração de pagamento. Erro: %s"),
    VALOR_TOTAL_INVALIDO("Invalid total amount. Total amount must be the sum of the total amount of items and the cash out amount.", "Erro na integração de pagamento. Valor total é inválido, a soma do valor total dos itens é diferente do valor total da capa"),
    TOKEN_MAL_FORMATADO("Malformed access_token", "Token mal formatado");

    private String mensagemOriginal;
    private String mensagemPtBr;

    public static String getMensagemPtBr(String mensagem) {
        return Arrays.stream(values())
                .filter(f -> Strings.containsAny(f.getMensagemOriginal(), mensagem))
                .map(x -> x.mensagemPtBr)
                .findFirst()
                .orElse(String.format(ERRO_GENERICO.getMensagemPtBr(), mensagem));
    }

}