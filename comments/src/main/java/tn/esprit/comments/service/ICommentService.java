package tn.esprit.comments.service;

import tn.esprit.comments.entity.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getCommentsByPostId(Long postId);
    Comment addComment(Comment comment, Long postId, Long userId);
    void likeComment(Long commentId, Long userId);
    void deleteComment(Long commentId);

    void reportComment(Long commentId);
}
