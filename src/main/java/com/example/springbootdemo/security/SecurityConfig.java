package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    // 「SpringBootWebSecurityConfiguration」で定義されているSecurityFilterChainを上書きする。
    // Beanとしてこちらを登録する。
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: CSRFを無効にしているが、どうして？
        http.csrf().disable();

        // "/login"は認証を無効にする
        http.authorizeHttpRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated();

        // 独自の認証Filterを設定
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(
            http.getSharedObject(AuthenticationConfiguration.class))));
        http.addFilterAfter(new LoginFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * とりあえずInMemoryでの簡易的な認証
     *
     * @return
     */
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails userDetails = User.withUsername("demo")
//            .password(
//                PasswordEncoderFactories
//                    .createDelegatingPasswordEncoder()
//                    .encode("demo"))
//            .roles("USER")
//            .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }
    @Bean
    public UserDetailsManager user(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }

    /**
     * この記述方法は非推奨 指定したパスについてSpring Securityが何もしなくなるため
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //return web -> web.ignoring().mvcMatchers("/items");
        return web -> web.ignoring().mvcMatchers("/abc");
    }
}
