package com.piseth.example.spring.phone_shop.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceFakeImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        List<AuthUser> authUsers = List.of(
                new AuthUser("Piseth Mao", passwordEncoder.encode("Piseth1711*#"), RoleEnum.SALE.getSimpleGrantedAuthority(), true, true, true, true),
                new AuthUser("Sophaneth Mao", passwordEncoder.encode("SoPhAnEtH"), RoleEnum.ADMIN.getSimpleGrantedAuthority(), true, true, true, true)
        );
        return authUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
