package com.students.security.roles;

import org.springframework.security.core.GrantedAuthority;

public enum SecurityRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
