package tn.esprit.posts.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.posts.entity.Post;
import tn.esprit.posts.service.IPostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostRestController {

    @Autowired
    private IPostService postService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.retrieveAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.retrievePost(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Post> addPost(@RequestBody Post post, @PathVariable Long userId) {
        return ResponseEntity.ok(postService.addPost(post, userId));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable Long id, @PathVariable Long userId) {
        postService.likePost(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
