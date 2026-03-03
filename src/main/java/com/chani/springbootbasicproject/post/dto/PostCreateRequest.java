package com.chani.springbootbasicproject.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PostCreateRequest(
        @NotBlank(message = "title은 필수입니다.") @Size(max = 120, message = "title은 최대 120자까지 가능합니다.") String title,
        @NotBlank(message = "content는 필수입니다.") @Size(max = 2000, message = "content는 최대 2000자까지 가능합니다.") String content,
        List<@NotBlank(message = "tag 값은 비어 있을 수 없습니다.") @Size(max = 50, message = "tag는 최대 50자까지 가능합니다.") String> tags
) {}
