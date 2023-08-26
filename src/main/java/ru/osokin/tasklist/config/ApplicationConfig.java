package ru.osokin.tasklist.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.osokin.tasklist.web.security.JwtTokenFilter;
import ru.osokin.tasklist.web.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {

    private final JwtTokenProvider tokenProvider;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .csrf().disable()
                .cors()
                .and()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Unauthorized.");
                }))
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Unauthorized.");
                })
                .and()
                .anonymous().disable()
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
}
