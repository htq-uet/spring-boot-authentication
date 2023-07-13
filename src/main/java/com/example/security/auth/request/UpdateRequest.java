package com.example.security.auth.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {

    private String firstname;
    private String lastname;
    private String username;
    @Size(min = 8, max = 16)
    private String password;
    @Size(min = 8, max = 16)
    private String newPassword;
    private String confirmPassword;


    public boolean newPasswordIsNull() {
        return newPassword == null;
    }

    public boolean confirmPasswordIsNull() {
        return confirmPassword == null;
    }
}
