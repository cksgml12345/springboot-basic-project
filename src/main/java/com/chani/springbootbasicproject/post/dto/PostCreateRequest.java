package com.chani.springbootbasicproject.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PostCreateRequest(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 2000) String content,
        List<@NotBlank @Size(max = 50) String> tags
) {}
