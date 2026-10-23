package org.example.pr4.model;

import org.springframework.security.core.GrantedAuthority;

public enum AppRole implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
