package com.example.security.auth.service;

import com.example.model.Token;
import com.example.repository.TokenRepository;
import com.example.repository.UserRepository;
import com.example.model.Role;
import com.example.model.User;
import com.example.security.auth.AuthenticationResponse;
import com.example.security.auth.request.AuthenticationRequest;
import com.example.security.auth.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        //log in console
        System.out.println("RegisterRequest: " + request);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        saveUserToken(savedUser, jwt);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    private void saveUserToken(User user, String jwt) {
        var token = Token.builder()
                .token(jwt)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserToken.size() > 0) {
            validUserToken.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
                tokenRepository.save(token);
            });
        }
        else {
            System.out.println("No valid token found");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow( () -> new RuntimeException("User not found"));

        var jwt = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
