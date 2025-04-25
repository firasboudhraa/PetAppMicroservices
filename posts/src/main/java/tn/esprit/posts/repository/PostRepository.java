package tn.esprit.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.posts.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
