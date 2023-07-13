package com.example.security.auth.service;

import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    private final TokenRepository tokenRepository;


    @Override
    public void logout (
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            //filterChain.doFilter(request, response);
            return;
        }
        jwt = authorizationHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);
    }
}
