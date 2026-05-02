package com.agenda.itic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.agenda.itic.service.CustomOAuth2UserService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${aws.cognito.region}")
    private String cognitoRegion;

    @Value("${aws.cognito.userPoolId}")
    private String cognitoUserPoolId;

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local", matchIfMissing = false)
    public SecurityFilterChain filterChainLocal(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/activitats/**").permitAll()
                    .requestMatchers("/salas/**").permitAll()
                        .requestMatchers("/correos-permitidos/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuaris/token").authenticated()
                    .requestMatchers("/usuaris/**").permitAll()
                    .requestMatchers("/dispositius/**").permitAll()
                    .requestMatchers("/roles/**").permitAll()
                    .requestMatchers("/recursos/**").permitAll()
                    .requestMatchers("/permisos/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "prod")
    public SecurityFilterChain filterChainProd(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/activitats/**").permitAll()
                    .requestMatchers("/salas/**").permitAll()
                        .requestMatchers("/correos-permitidos/**").permitAll()
                    .requestMatchers("/usuaris/**").permitAll()
                    .requestMatchers("/dispositius/**").permitAll()
                    .requestMatchers("/roles/**").permitAll()
                    .requestMatchers("/recursos/**").permitAll()
                    .requestMatchers("/permisos/**").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                    jwt -> jwt.jwkSetUri("https://cognito-idp." + cognitoRegion + ".amazonaws.com/" + cognitoUserPoolId + "/.well-known/jwks.json")
                ))
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/oauth/google/home", true)
                        .failureUrl("/login.html?error=unauthorized"));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers("/activitats")
            .requestMatchers("/salas")
            .requestMatchers("/usuaris/token")
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}
