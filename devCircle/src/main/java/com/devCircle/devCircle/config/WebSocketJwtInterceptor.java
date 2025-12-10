package com.devCircle.devCircle.config;


import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.UserRepository;
import com.devCircle.devCircle.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketJwtInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        System.out.println(">>> Handshake interceptor called");

        // 1️⃣ Try extract JWT from Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 2️⃣ If not in header → try query param: ?token=Bearer%20xxx
        if (authHeader == null) {
            URI uri = request.getURI();
            String query = uri.getQuery();

            if (query != null && query.contains("token=")) {
                authHeader = query.substring(query.indexOf("token=") + 6);
                authHeader = authHeader.replace("%20", " "); // decode
            }
        }

        String token;
        if (authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            token = authHeader; // allow plain token
        }
        String email;

        try {
            email = jwtService.extractEmail(token);
        } catch (Exception e) {
            return false;
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !jwtService.isTokenValid(token, email)) {
            return false;
        }

        // Save the authenticated user for the handshake
        attributes.put("user", user);

        return true; // allow connection
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {
    }
}