package com.piseth.example.spring.phone_shop.config.security;

import com.piseth.example.spring.phone_shop.config.jwt.JwtLoginFilter;
import com.piseth.example.spring.phone_shop.config.jwt.TokenVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new TokenVerifyFilter(), JwtLoginFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**")
                        .permitAll()
//                        .requestMatchers("/brands")
//                        .hasRole("SALE") // "SALE"
                        .requestMatchers(HttpMethod.POST, "/brands")
                        .hasAnyAuthority("brand:write")
                        .requestMatchers(HttpMethod.GET, "/brands")
                        .hasAnyAuthority("brand:read")
                        .anyRequest()
                        .authenticated())
                .addFilter(new JwtLoginFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class))))
                .authenticationProvider(getAuthenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("Piseth Mao")
                .password(passwordEncoder.encode("PiSeTh1711*#"))
//                .roles("SALE") // ROLE_SALE
                .authorities("ROLE_SALE", MODEL_READ.getDescription(), BRAND_READ.getDescription()) // collection of grantedAuthority
                .build();
        UserDetails userDetails1 = User.builder()
                .username("Sophaneth Mao")
                .password(passwordEncoder.encode("SoPhAnEtH"))
//                .roles("ADMIN") // ROLE_ADMIN
                .authorities("ROLE_ADMIN", BRAND_WRITE.getDescription(), BRAND_READ.getDescription(), MODEL_WRITE.getDescription())
                .build();
        return new InMemoryUserDetailsManager(userDetails, userDetails1);
    }
    */
    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
