package com.lab4tech.hive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity // Activate Spring's Web Security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection to REST API ** Should handle it with classical frontend **
                .httpBasic(AbstractHttpConfigurer::disable) // Disable default config
                .authorizeHttpRequests(authorize -> authorize   // Conf HTTP request authorisations
                        .requestMatchers("/users/**").permitAll()  // authorize all requests post / get
                        .anyRequest().authenticated()    // other request must be authentified
                );
        return http.build();
    }
}
