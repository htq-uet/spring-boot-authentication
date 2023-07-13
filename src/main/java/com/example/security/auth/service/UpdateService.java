package com.example.security.auth.service;

import com.example.repository.UserRepository;
import com.example.security.auth.request.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public String update(UpdateRequest request) {
        //verify password
        //if password is correct, update user
        //else throw exception

        String username = request.getUsername();
        String password = (request.getPassword());
        String firstname = request.getFirstname();
        String lastname = request.getLastname();
        String newPassword = request.getNewPassword();
        String confirmPassword = (request.getConfirmPassword());

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            if (request.newPasswordIsNull() && request.confirmPasswordIsNull()) {
                user.setFirstname(firstname);
                user.setLastname(lastname);
                userRepository.save(user);
                return "User updated";
            } else if (Objects.equals(newPassword, confirmPassword)) {
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return "User updated";
            } else {
                throw new RuntimeException("Passwords do not match");
            }
        }
        throw new RuntimeException("Incorrect password");
    }
}
