package com.gabriel.firebase.example;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final var fauth = FirebaseAuth.getInstance();
            final var user = fauth.getUserByEmail(email);
            return new UserDetailsImpl(user.getUid(), user.getEmail());
        } catch (Throwable throwable) {
            throw new UsernameNotFoundException(throwable.getMessage());
        }

    }
    
}
