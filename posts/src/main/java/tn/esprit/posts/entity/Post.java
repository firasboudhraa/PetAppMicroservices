package tn.esprit.posts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int likes;
    private LocalDateTime createdAt;
    private String imageUrl; // Store image URL instead of binary data

    @Enumerated(EnumType.STRING)
    private PostTypeEnum type;

    private Long userId; // Instead of User entity, store only user ID

    @ElementCollection
    private Set<Long> likedBy = new HashSet<>();

    public void like(Long userId) {
        if (!likedBy.contains(userId)) {
            this.likes++;
            likedBy.add(userId);
        }
    }

    // Manually added getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PostTypeEnum getType() {
        return type;
    }

    public void setType(PostTypeEnum type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<Long> likedBy) {
        this.likedBy = likedBy;
    }
}
