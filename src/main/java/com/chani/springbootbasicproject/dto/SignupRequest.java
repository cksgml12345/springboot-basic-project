package com.chani.springbootbasicproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "username은 필수입니다.") @Size(min = 3, max = 50, message = "username은 3~50자여야 합니다.") String username,
        @NotBlank(message = "email은 필수입니다.") @Email(message = "유효한 이메일 형식이어야 합니다.") String email,
        @NotBlank(message = "password는 필수입니다.") @Size(min = 8, max = 100, message = "password는 8~100자여야 합니다.") String password
) {
}
