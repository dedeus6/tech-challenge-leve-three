package br.com.fiap.config;

import br.com.fiap.app.exception.ForbiddenException;
import br.com.fiap.utils.Strings;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;

//public class InterceptorCliente implements HandlerInterceptor {
public class InterceptorPedido {

   // @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
            return true;

        var x = request.getPathInfo();

        String authorization = request.getHeader("Authorization");
        if (!Strings.startsWith(authorization, "bearer "))
            return true;

        String accessToken = authorization.substring(7);

        var decodeJwt = decode(JwtConfig.ALGORITHM, JwtConfig.ISSUER, accessToken);
        if (decodeJwt.getExpiresAt().before(Date.from(Instant.now())))
            throw new ForbiddenException("Token expirado", HttpStatus.FORBIDDEN);

        return true;
    }

    public static DecodedJWT decode(Algorithm algorithm, String issuer, String token) {
        Verification verification = JWT.require(algorithm);
        verification.withIssuer(issuer);
        JWTVerifier verifier = verification.build();
        return verifier.verify(token);
    }

}
