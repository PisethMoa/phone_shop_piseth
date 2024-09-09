package com.piseth.example.spring.phone_shop.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String secretKey = "abcdefghijklmnopqrstuvwxyz1234567891011121314151617";
        String token = Jwts.builder()
                .claim("sub", ((UserDetails) authResult.getPrincipal()).getUsername())
                .claim("iat", new Date())
                .claim("authorities", authResult.getAuthorities())
                .claim("exp", java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                .claim("iss", "phone_shop.com")
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
        response.setHeader("authorization", "Bearer " + token);
    }
}
