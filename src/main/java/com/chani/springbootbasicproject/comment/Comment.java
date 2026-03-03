package com.chani.springbootbasicproject.comment;

import com.chani.springbootbasicproject.domain.User;
import com.chani.springbootbasicproject.post.Post;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    protected Comment() {
    }

    public Comment(String content, Post post, User author) {
        this.content = content;
        this.post = post;
        this.author = author;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public Post getPost() { return post; }
    public User getAuthor() { return author; }
}
