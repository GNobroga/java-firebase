package com.gabriel.firebase.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            return;
        }

       buildResponseError((HttpServletResponse) servletResponse);
    }

    private void buildResponseError(final HttpServletResponse servletResponse) throws JsonProcessingException, IOException {
        final var objectMapper = new ObjectMapper();
        final var payload = new HashMap<>() {
            {
                put("title", "Unauthorized");
                put("message", "Não autorizado");
            }
        };

        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        servletResponse.getWriter().write(objectMapper.writeValueAsString(payload));
    }

    private boolean tryAuthenticate(final String idToken) {
        try {
            final var fauth = FirebaseAuth.getInstance();
            final var decodedToken = fauth.verifyIdToken(idToken);
            final var user = userDetailsService.loadUserByUsername(decodedToken.getEmail());
            final var authentication = new FirebaseAuthenticationToken(user);
            final var context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            return context.getAuthentication().isAuthenticated();
        } catch(Throwable throwable) {
            System.out.println("dsdsd");
            return false;
        }
    }

    private static String getIdToken(final HttpServletRequest request) {
        final var authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.split(" ")[1];
        }
        return null;
    }

    
}
