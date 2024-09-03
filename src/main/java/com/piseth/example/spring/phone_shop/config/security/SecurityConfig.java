package com.piseth.example.spring.phone_shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**")
                        .permitAll()
                        .requestMatchers("/brands")
                        .hasRole("SALE")
                        .anyRequest()
                        .authenticated())
                .httpBasic(withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("Piseth Mao")
                .password(passwordEncoder.encode("PiSeTh1711*#"))
                .roles("SALE") // ROLE_SALE
                .build();
        UserDetails userDetails1 = User.builder()
                .username("Sophaneth Mao")
                .password(passwordEncoder.encode("SoPhAnEtH"))
                .roles("ADMIN") // ROLE_ADMIN
                .build();
        return new InMemoryUserDetailsManager(userDetails, userDetails1);
    }
}
