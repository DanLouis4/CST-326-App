package com.gcu.charactertracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/auth/login",
                    "/auth/register",
                    "/characters",
                    "/search",
                    "/about",
                    "/css/**",
                    "/images/**",
                    "/js/**",
                    "/webjars/**"
                ).permitAll()

                .requestMatchers("/admin/**").hasRole("ADMIN")

                .requestMatchers(
                    "/users/**",
                    "/favorites/**",
                    "/characters/create",
                    "/characters/create/**",
                    "/characters/edit/**",
                    "/characters/delete/**"
                ).authenticated()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/characters", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
};