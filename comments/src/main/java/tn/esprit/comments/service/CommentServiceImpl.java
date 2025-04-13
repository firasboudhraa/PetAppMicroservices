package tn.esprit.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.comments.entity.Comment;
import tn.esprit.comments.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment addComment(Comment comment, Long postId, Long userId) {
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setCreatedAt(LocalDateTime.now());

        // 🔍 Appliquer le filtre sur le contenu avant d'enregistrer
        comment.setContent(filterBadWords(comment.getContent()));

        return commentRepository.save(comment);
    }

    @Override
    public void likeComment(Long commentId, Long userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.like(userId);
            commentRepository.save(comment);
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void reportComment(Long commentId) {

    }

    private static final List<String> BAD_WORDS = List.of("merde", "isreal", "con", "putain", "fuck", "shit");

    private String filterBadWords(String content) {
        String filteredContent = content;
        for (String badWord : BAD_WORDS) {
            String stars = "*".repeat(badWord.length());
            filteredContent = filteredContent.replaceAll("(?i)\\b" + badWord + "\\b", stars);
        }
        return filteredContent;
    }


}
