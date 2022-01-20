package ch.bbcag.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthFilter implements Filter {

    private String secret;

    public JWTAuthFilter(String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        String[] parts = authorizationHeader.split(" ");

        if (parts.length < 2) {
            chain.doFilter(request, response);
            return;
        }

        String token = parts[1];

        try {
            String user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (TokenExpiredException e) {
            res.addHeader("TOKEN-EXPIRED", "true");
        } catch (Exception e) {

        } finally {
            chain.doFilter(request, response);
        }
    }
}
