package com.gabriel.firebase.example;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
    
    private final Object principal;

    public FirebaseAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
       return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
    
}
