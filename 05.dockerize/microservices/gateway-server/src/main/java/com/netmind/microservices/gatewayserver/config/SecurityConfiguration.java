package com.netmind.microservices.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable());

        http.authorizeExchange()
                .pathMatchers(
                        "/configuration/ui",
                        "/configuration/security",
                        "/auth/login",
                        "/v3/**",
                        "/docs/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/*-service/**",
                        "/swagger-ui.html"
                ).permitAll()
                .pathMatchers("/products/**").permitAll() // with oauth must be commented
                .pathMatchers("/orders/**").permitAll()
                .pathMatchers("/accounts/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }

}