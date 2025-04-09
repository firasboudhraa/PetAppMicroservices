package tn.esprit.posts.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.posts.entity.Post;
import tn.esprit.posts.entity.PostTypeEnum;
import tn.esprit.posts.service.IPostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/posts")
public class PostRestController {

    @Autowired
    private IPostService postService;

    private static final String UPLOAD_DIR = "posts/uploads/";


    /**
     * Récupérer tous les posts
     */
    @GetMapping
    public List<Post> getPosts() {
        return postService.retrieveAllPosts();
    }

    /**
     * Récupérer un post par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.retrievePost(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    /**
     * Ajouter un post avec image
     * URL : /posts/upload/{userId}
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Post> addPost(
            @PathVariable Long userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("type") String type,
            @RequestParam("image") MultipartFile image) {

        try {
            // Save the image and get the URL (you should implement this method to save the image)
            String imageUrl = saveImage(image);

            // Create the Post object and set its attributes
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setType(PostTypeEnum.valueOf(type.toUpperCase())); // Assuming type is enum
            post.setImageUrl(imageUrl);

            // Add the user ID to the post
            post.setUserId(userId); // Assuming you have a userId field in your Post entity

            // Save the post and return the response
            return ResponseEntity.ok(postService.addPost(post, userId));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long userId,
            @PathVariable Long postId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("type") String type,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            // Fetch the existing post from the database by ID
            Post post = postService.retrievePost(postId);
            if (post == null) {
                return ResponseEntity.notFound().build();  // Return 404 if post doesn't exist
            }

            // Update the post fields
            post.setTitle(title);
            post.setContent(content);
            post.setType(PostTypeEnum.valueOf(type.toUpperCase())); // Assuming type is enum

            // Handle the image update (if a new image is provided)
            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                post.setImageUrl(imageUrl); // Update the image URL
            }

            // Add or update the user ID for the post
            post.setUserId(userId);

            // Save the updated post and return the response
            Post updatedPost = postService.addPost(post, userId);  // Assuming addPost handles saving the post
            return ResponseEntity.ok(updatedPost);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);  // Handle errors (e.g., image upload)
        }
    }


    /**
     * Liker un post par un utilisateur
     * URL : /posts/{id}/like/{userId}
     */
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable Long id, @PathVariable Long userId) {
        postService.likePost(id, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Supprimer un post
     * URL : /posts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private String saveImage(MultipartFile image) throws IOException {
        // Get the original filename of the image
        String fileName = image.getOriginalFilename();

        // Create a Path object to the directory where the image will be stored
        //Path uploadPath = Paths.get(UPLOAD_DIR);
        Path uploadPath = Paths.get(System.getProperty("user.dir"), UPLOAD_DIR);

        // Create the directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create a path for the new file
        Path filePath = uploadPath.resolve(fileName);

        // Save the file locally
        image.transferTo(filePath.toFile());

        // Return the file path (you can use this in the database)
        return fileName;  // You can return a relative URL if you wish
    }
}
