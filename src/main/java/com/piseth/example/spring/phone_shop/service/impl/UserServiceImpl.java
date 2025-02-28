package com.piseth.example.spring.phone_shop.service.impl;

import com.piseth.example.spring.phone_shop.config.security.AuthUser;
import com.piseth.example.spring.phone_shop.config.security.UserService;
import com.piseth.example.spring.phone_shop.entity.Role;
import com.piseth.example.spring.phone_shop.entity.User;
import com.piseth.example.spring.phone_shop.exception.ApiException;
import com.piseth.example.spring.phone_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found."));
//        AuthUser authUser = new AuthUser();
        AuthUser authUser = AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
        return Optional.ofNullable(authUser);
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<SimpleGrantedAuthority> set = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
//        Stream<Stream<SimpleGrantedAuthority>> streamStream =
        Set<SimpleGrantedAuthority> set1 = roles.stream()
                .flatMap(this::toStream)
                .collect(Collectors.toSet());
        set1.addAll(set);
        return set1;
    }

    private Stream<SimpleGrantedAuthority> toStream(Role role) {
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));
    }
}
