package br.com.fiap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

//@Configuration
public class InterceptorConfig {

    //@Bean("mappedInterceptorCliente")
    public MappedInterceptor mappedInterceptorCliente() {
        return null;//new MappedInterceptor(new String[]{"/api/v1/pedido/**"}, new InterceptorCliente());
    }

}
