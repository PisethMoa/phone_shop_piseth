package com.piseth.example.spring.phone_shop.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.piseth.example.spring.phone_shop.config.security.PermissionEnum.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {
    ADMIN(Set.of(BRAND_WRITE, BRAND_READ, MODEL_WRITE, MODEL_READ)),
    SALE(Set.of(BRAND_READ, MODEL_READ));
    private Set<PermissionEnum> permissions;

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
        Set<SimpleGrantedAuthority> grantedAuthorities = this.permissions.stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.getDescription()))
                .collect(Collectors.toSet());
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + this.name());
        grantedAuthorities.add(simpleGrantedAuthority);
        System.out.println(grantedAuthorities);
        return grantedAuthorities;
    }
}
