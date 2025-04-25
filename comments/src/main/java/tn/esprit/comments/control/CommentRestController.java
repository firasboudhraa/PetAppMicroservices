package tn.esprit.comments.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.comments.entity.Comment;
import tn.esprit.comments.service.ICommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentRestController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment,
                                              @PathVariable Long postId,
                                              @PathVariable Long userId) {
        return ResponseEntity.ok(commentService.addComment(comment, postId, userId));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likeComment(@PathVariable Long id, @PathVariable Long userId) {
        commentService.likeComment(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

}
