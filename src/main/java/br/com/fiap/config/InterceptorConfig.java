package br.com.fiap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class InterceptorConfig {

    @Bean("mappedInterceptorPedido")
    public MappedInterceptor mappedInterceptorPedido() {
        return new MappedInterceptor(new String[]{"/api/v1/pedido/**"}, new InterceptorPedido());
    }

}
