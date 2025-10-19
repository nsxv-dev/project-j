package com.devCircle.devCircle.config;

import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.UserRepository;
import com.devCircle.devCircle.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1️⃣ Get Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        // 2️⃣ Skip if header is missing or doesn’t start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ Extract token (remove “Bearer ”)
        jwt = authHeader.substring(7);
        email = jwtService.extractEmail(jwt);

        // 4️⃣ Check if user is not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user from DB
            User userDetails = userRepository.findByEmail(email)
                    .orElse(null);

            // 5️⃣ Validate token
            if (userDetails != null && jwtService.isTokenValid(jwt, userDetails.getEmail())) {
                // 6️⃣ Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7️⃣ Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8️⃣ Continue the chain
        filterChain.doFilter(request, response);
    }
}
