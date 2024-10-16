package org.hyeong.api1014.security.config;

import org.hyeong.api1014.security.filter.JWYCheckFilter;
import org.hyeong.api1014.security.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

    @Bean
    public JWTUtil jwtUtil() {

        return new JWTUtil();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(config -> config.disable());

        http.sessionManagement(config ->
                config.sessionCreationPolicy(SessionCreationPolicy.NEVER));

        http.authorizeRequests(
                (auth -> auth.requestMatchers("/api/v1/product/list").permitAll())
        );

        http.csrf(config -> config.disable());

        http.addFilterBefore(new JWYCheckFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


