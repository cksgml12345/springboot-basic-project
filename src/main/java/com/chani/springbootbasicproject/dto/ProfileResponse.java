package com.chani.springbootbasicproject.dto;

public record ProfileResponse(
        Long id,
        String username,
        String email
) {
}
