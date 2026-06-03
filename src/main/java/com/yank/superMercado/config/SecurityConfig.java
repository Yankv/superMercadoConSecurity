package com.yank.superMercado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yank.superMercado.jwt.CustomUserDetailsService;
import com.yank.superMercado.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sesion -> sesion.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/api/auth/**")
                        .permitAll()

                        // Se restringe el acceso a las estadísticas solo a usuarios
                        // con rol ADMIN
                        .requestMatchers("/api/estadisticas/**").hasRole("ADMIN")

                        // Se permite el acceso público a los endpoints GET que no tienen
                        // segmentos adicionales
                        .requestMatchers(HttpMethod.GET, "/api/productos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/sucursales").permitAll()

                        // Se restringe el acceso a los endpoints GET con segmentos
                        // adicionales a usuarios con rol EMPLOYEE o ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/sucursales/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/ventas/**").hasAnyRole("EMPLOYEE", "ADMIN")

                        // Se restringe el acceso a los endpoints de creación de ventas a usuarios
                        // con rol EMPLOYEE o ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasAnyRole("EMPLOYEE", "ADMIN")

                        // Se restringe el acceso a los endpoints de escritura a usuarios
                        // con rol ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
