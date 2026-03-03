package com.chani.springbootbasicproject.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
        @NotBlank(message = "content는 필수입니다.") @Size(max = 1000, message = "content는 최대 1000자까지 가능합니다.") String content
) {}
