package tn.esprit.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.comments.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
