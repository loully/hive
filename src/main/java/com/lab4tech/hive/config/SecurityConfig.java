package com.lab4tech.hive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableMethodSecurity //For authorisation using Spring Expression Language - checking for Role before delete user
@EnableWebSecurity // Activate Spring's Web Security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection to REST API ** Should handle it with classical frontend **
                .httpBasic(AbstractHttpConfigurer::disable) // Disable default config
                .authorizeHttpRequests(authorize -> authorize   // Conf HTTP request authorisations
                        .requestMatchers("/users/**").permitAll()  // authorize all requests post / get
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN") // delete
                        .anyRequest().authenticated()    // other request must be authentified
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
