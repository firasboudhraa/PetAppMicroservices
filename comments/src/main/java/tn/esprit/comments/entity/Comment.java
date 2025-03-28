package tn.esprit.comments.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private Long postId;  // Associated Post ID
    private Long userId;  // User ID of the commenter

    private int likes;

    @ElementCollection
    private Set<Long> likedBy = new HashSet<>();

    public Comment() {
    }

    public Comment(Long id, String content, LocalDateTime createdAt, Long postId, Long userId, int likes, Set<Long> likedBy) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.postId = postId;
        this.userId = userId;
        this.likes = likes;
        this.likedBy = likedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<Long> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<Long> likedBy) {
        this.likedBy = likedBy;
    }

    public void like(Long userId) {
        if (!likedBy.contains(userId)) {
            this.likes++;
            likedBy.add(userId);
        }
    }
}
