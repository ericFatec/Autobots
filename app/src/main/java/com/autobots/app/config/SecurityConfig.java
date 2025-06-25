package com.autobots.app.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.autobots.app.adapters.CustomAuthenticationProvider;
import com.autobots.app.adapters.UserDetailsImpl;
import com.autobots.app.adapters.UserDetailsServiceImpl;
import com.autobots.app.filters.CustomAuthenticationFilter;
import com.autobots.app.filters.JwtAuthorizationFilter;
import com.autobots.app.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired private CustomAuthenticationProvider authProvider;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager(http);

        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authManager, objectMapper);
        filter.setFilterProcessesUrl("/auth/login");

        filter.setAuthenticationSuccessHandler((req, res, auth) -> {
            UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
            String token = jwtUtil.generateToken(user);

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            objectMapper.writeValue(res.getWriter(), Map.of(
                "access_token", token,
                "token_type", "Bearer"
            ));
        });

        filter.setAuthenticationFailureHandler((req, res, ex) -> {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"erro\": \"Credenciais inválidas\"}");
        });

        JwtAuthorizationFilter jwtFilter = new JwtAuthorizationFilter(jwtUtil, userDetailsService);

        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(
                    "/", 
                    "/auth/login", 
                    "/auth/logout",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/error",
                    "/swagger-ui.html", 
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/configuration/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                    res.setContentType("application/json");
                    res.getWriter().write("{\"message\": \"Logout successful\"}");
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .authenticationManager(authManager)
            .addFilterBefore(jwtFilter, CustomAuthenticationFilter.class)
            .addFilter(filter)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider)
            .build();
    }
}