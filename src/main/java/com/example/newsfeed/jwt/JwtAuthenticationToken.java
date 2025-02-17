package com.example.newsfeed.jwt;

import com.example.newsfeed.jwt.entity.AuthUsers;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthUsers authUsers;

    public JwtAuthenticationToken(AuthUsers authUsers) {
        super(null);
        this.authUsers = authUsers;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authUsers;
    }
}