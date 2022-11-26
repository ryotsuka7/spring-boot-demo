package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // TODO: 定数については切り出すこと
        // ログインパスの指定
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        // ログインパラメータの設定
        setUsernameParameter("username");
        setPasswordParameter("password");

        // ログイン成功時
        this.setAuthenticationSuccessHandler((req, res, ex) -> {

            BizClaims bizClaims = new BizClaims();
            // TODO: ここは要修正。アプリケーション独自の情報をJWTに格納するように設計すること。
            // TODO: DBに格納している値を取得して、tokenに含める
            bizClaims.put("username", "otsuka");
            bizClaims.put("role", "user");

            String token = JwtUtils.createToken("loginId", bizClaims);
            res.setHeader(JwtUtils.HEADER_AUTHORIZATION, JwtUtils.BEARER + token);

            // TODO: 試しにtokenを解析
            Boolean isVerifyToken = JwtUtils.verifyToken(token);
            Map<String, Object> claims = JwtUtils.getClaims(token);

            res.setStatus(HttpStatus.OK.value());
        });

        // ログイン失敗時
        this.setAuthenticationFailureHandler((req, res, ex) -> {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequest.class);
            return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
