package com.piseth.example.spring.phone_shop.config.security;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findUserByUsername(String username);
}
