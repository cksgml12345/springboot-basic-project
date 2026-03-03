package com.chani.springbootbasicproject.post;

import com.chani.springbootbasicproject.comment.dto.CommentCreateRequest;
import com.chani.springbootbasicproject.post.dto.CommentResponse;
import com.chani.springbootbasicproject.post.dto.PostCreateRequest;
import com.chani.springbootbasicproject.post.dto.PostResponse;
import com.chani.springbootbasicproject.post.dto.PostUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody PostCreateRequest request, Authentication authentication) {
        return postService.create(request, authentication.getName());
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id,
                               @Valid @RequestBody PostUpdateRequest request,
                               Authentication authentication) {
        return postService.update(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, Authentication authentication) {
        postService.delete(id, authentication.getName());
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(@PathVariable Long id,
                                      @Valid @RequestBody CommentCreateRequest request,
                                      Authentication authentication) {
        return postService.addComment(id, request, authentication.getName());
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              Authentication authentication) {
        postService.deleteComment(postId, commentId, authentication.getName());
    }
}
