package com.example.springbootdemo.security;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String value = request.getHeader(JwtUtils.HEADER_AUTHORIZATION);
        if (Objects.isNull(value) || !value.startsWith(JwtUtils.BEARER)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        // Tokenを検証
        String token = value.substring(JwtUtils.BEARER.length());
        if (!JwtUtils.verifyToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        // Tokenが正常なものであるとき
        // TODO: claimsの持ち方をきちんと設計すること
        // TODO: Tokenから各種情報を取得して、Controllerからも取得できるように設計すること。
        Claims claims = JwtUtils.getClaims(token);
        BizClaims bizClaims = BizClaims.getBizClaimsFromClaims(claims);
        SecurityContextHolder.getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                    new ArrayList<>()));
        filterChain.doFilter(request, response);
    }
}
