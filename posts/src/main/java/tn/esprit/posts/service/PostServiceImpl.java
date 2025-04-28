package tn.esprit.posts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.posts.entity.Post;
import tn.esprit.posts.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final EmailService emailService;

    // 🔧 Constructeur manuel
    @Autowired
    public PostServiceImpl(PostRepository postRepository, EmailService emailService) {
        this.postRepository = postRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }


    public Post retrievePost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Post addPost(Post post, Long userId) {
        post.setUserId(userId);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.like(userId); // gestion des doublons dans la méthode like()
            postRepository.save(post);
        }
    }



<<<<<<< HEAD
    public void deletePost(Long postId, String title, String firstName, String email) {
=======
    @Override
    public void deletePost(Long postId, String title, String author, String email) {
>>>>>>> c3d9507ad79d261ff9347f2c2b56b58830a98c30
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        postRepository.delete(post);

<<<<<<< HEAD
        // Send email
        emailService.sendPostDeletionEmail(title, firstName, email);
=======
        // After delete, send email with given info
        emailService.sendPostDeletionEmail(title, author, email);
>>>>>>> c3d9507ad79d261ff9347f2c2b56b58830a98c30
    }

    @Override
    public void deletePostWithouMail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        postRepository.delete(post);

    }
}
