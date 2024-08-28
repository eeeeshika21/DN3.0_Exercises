package com.example.bookstoreapi.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        super(authManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, jakarta.servlet.ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
