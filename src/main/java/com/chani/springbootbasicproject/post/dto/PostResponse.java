package com.chani.springbootbasicproject.post.dto;

import java.util.List;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        List<String> tags,
        List<CommentResponse> comments
) {}
