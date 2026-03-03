package com.chani.springbootbasicproject.post;

import com.chani.springbootbasicproject.comment.Comment;
import com.chani.springbootbasicproject.comment.CommentRepository;
import com.chani.springbootbasicproject.comment.dto.CommentCreateRequest;
import com.chani.springbootbasicproject.domain.User;
import com.chani.springbootbasicproject.exception.BadRequestException;
import com.chani.springbootbasicproject.exception.ForbiddenOperationException;
import com.chani.springbootbasicproject.exception.ResourceNotFoundException;
import com.chani.springbootbasicproject.post.dto.CommentResponse;
import com.chani.springbootbasicproject.post.dto.PostCreateRequest;
import com.chani.springbootbasicproject.post.dto.PostResponse;
import com.chani.springbootbasicproject.post.dto.PostUpdateRequest;
import com.chani.springbootbasicproject.repository.UserRepository;
import com.chani.springbootbasicproject.tag.Tag;
import com.chani.springbootbasicproject.tag.TagRepository;
import jakarta.transaction.Transactional;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       TagRepository tagRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request, String email) {
        User author = findUserByEmail(email);
        Post post = new Post(request.title(), request.content(), author);
        post.setTags(resolveTags(request.tags()));
        Post saved = postRepository.save(post);
        return toResponse(saved);
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request, String email) {
        Post post = findPost(id);
        if (!post.getAuthor().getEmail().equals(email)) {
            throw new ForbiddenOperationException("본인 글만 수정할 수 있습니다.");
        }
        post.update(request.title(), request.content());
        post.setTags(resolveTags(request.tags()));
        return toResponse(post);
    }

    public List<PostResponse> findAll() {
        return postRepository.findAllByOrderByIdDesc().stream().map(this::toResponse).toList();
    }

    public PostResponse findById(Long id) {
        return toResponse(findPost(id));
    }

    @Transactional
    public void delete(Long id, String email) {
        Post post = findPost(id);
        if (!post.getAuthor().getEmail().equals(email)) {
            throw new ForbiddenOperationException("본인 글만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }

    @Transactional
    public CommentResponse addComment(Long postId, CommentCreateRequest request, String email) {
        Post post = findPost(postId);
        User author = findUserByEmail(email);
        Comment comment = commentRepository.save(new Comment(request.content(), post, author));
        return new CommentResponse(comment.getId(), comment.getContent(), comment.getAuthor().getUsername());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String email) {
        Post post = findPost(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BadRequestException("해당 게시글의 댓글이 아닙니다.");
        }
        if (!comment.getAuthor().getEmail().equals(email)) {
            throw new ForbiddenOperationException("본인 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
    }

    private Set<Tag> resolveTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }

        Set<Tag> tags = new HashSet<>();
        for (String raw : tagNames) {
            String normalized = raw.trim().toLowerCase();
            if (normalized.isEmpty()) {
                continue;
            }
            Tag tag = tagRepository.findByName(normalized)
                    .orElseGet(() -> tagRepository.save(new Tag(normalized)));
            tags.add(tag);
        }
        return tags;
    }

    private PostResponse toResponse(Post post) {
        List<String> tags = post.getTags().stream().map(Tag::getName).sorted().toList();
        List<CommentResponse> comments = post.getComments().stream()
                .map(c -> new CommentResponse(c.getId(), c.getContent(), c.getAuthor().getUsername()))
                .toList();

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                tags,
                comments
        );
    }
}
