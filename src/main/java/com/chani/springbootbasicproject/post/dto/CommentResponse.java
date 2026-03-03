package com.chani.springbootbasicproject.post.dto;

public record CommentResponse(
        Long id,
        String content,
        String author
) {}
