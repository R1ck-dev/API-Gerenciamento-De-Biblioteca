package com.henrique.api_gerenciamento_de_biblioteca.Security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@ConfigurationProperties(prefix = "security.config") // Trazendo a leitura das propriedades para cá
public class WebSecurityConfig {

    // As propriedades do JWT agora vivem aqui
    public static String PREFIX;
    public static String KEY;
    public static Long EXPIRATION;

    // Setters para o Spring injetar os valores do application.properties
    public void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public void setKey(String key) {
        KEY = key;
    }

    public void setExpiration(Long expiration) {
        EXPIRATION = expiration;
    }

    // Bean único para o PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean único e principal para a cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF, comum em APIs stateless

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Garante que a aplicação não vai criar sessões

                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/cadastro").permitAll() // Assumindo que /users é seu endpoint de cadastro

                        // Endpoints protegidos por ROLE
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN") // hasRole é mais simples se for só uma role

                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated())

                // Adiciona o filtro de JWT antes do filtro padrão de autenticação
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}