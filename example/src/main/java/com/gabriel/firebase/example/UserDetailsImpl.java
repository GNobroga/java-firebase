package com.gabriel.firebase.example;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    private final String uid;
    private final String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
       return email;
    }
    
}
