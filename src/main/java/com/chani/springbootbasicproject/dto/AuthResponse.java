package com.chani.springbootbasicproject.dto;

public record AuthResponse(
        String token,
        String username,
        String email
) {
}
