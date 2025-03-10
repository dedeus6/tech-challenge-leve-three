package br.com.fiap.config;

import br.com.fiap.app.exception.BusinessException;
import br.com.fiap.utils.JwtBuilder;
import br.com.fiap.utils.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class InterceptorPedido implements HandlerInterceptor {

    private static final String V1_PEDIDO = "/api/v1/pedido";
    private static final String POST = "POST";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Strings.diff(request.getMethod(), POST) && Strings.diff(request.getRequestURI(), V1_PEDIDO))
            return true;

        String authorization = request.getHeader("Authorization");
        if (!Strings.startsWith(authorization, "bearer "))
            throw new BusinessException("Authorization enviado está inválido", HttpStatus.UNPROCESSABLE_ENTITY);

        String accessToken = authorization.substring(7);
        JwtBuilder.decode(JwtConfig.ALGORITHM, JwtConfig.ISSUER, accessToken);
        return true;
    }
}
