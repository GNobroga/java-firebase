package com.gabriel.firebase.example;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.firebase.auth.FirebaseAuth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityFirebaseTokenFilter implements Filter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final var request = (HttpServletRequest) servletRequest;
        final var idToken = getIdToken(request);
        if (Objects.nonNull(idToken) && tryAuthenticate(idToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean tryAuthenticate(String idToken) {
        try {
            final var fauth = FirebaseAuth.getInstance();
            final var decodedToken = fauth.verifyIdToken(idToken);
            final var user = userDetailsService.loadUserByUsername(decodedToken.getEmail());
            final var authentication = new UsernamePasswordAuthenticationToken(user, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch(Throwable throwable) {
            return false;
        }
    }

    private static String getIdToken(HttpServletRequest request) {
        final var authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.split(" ")[1];
        }
        return null;
    }

    
}
