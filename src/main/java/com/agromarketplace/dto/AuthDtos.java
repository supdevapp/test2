package com.agromarketplace.dto;

import com.agromarketplace.enums.Role;
import jakarta.validation.constraints.*;

public class AuthDtos {
    public record RegisterRequest(
            @NotBlank String username,
            @Email String email,
            @Size(min = 6) String password,
            @NotNull Role role
    ) {}

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password
    ) {}

    public record AuthResponse(String token) {}
}
