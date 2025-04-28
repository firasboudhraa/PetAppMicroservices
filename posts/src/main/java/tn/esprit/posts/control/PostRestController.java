package tn.esprit.posts.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.posts.entity.Post;
import tn.esprit.posts.entity.PostTypeEnum;
import tn.esprit.posts.service.EmailService;
import tn.esprit.posts.service.IPostService;

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

    @Autowired
    private EmailService emailService;

    private static final String UPLOAD_DIR = "posts/uploads/";

    /**
     * Récupérer tous les posts
     */
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.retrieveAllPosts();
        return ResponseEntity.ok(posts);
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
     * Ajouter un post avec image, latitude et longitude
     * URL : /posts/{userId}
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Post> addPost(
            @PathVariable Long userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("type") String type,
            @RequestParam("image") MultipartFile image,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {

        try {
            // Sauvegarder l'image et obtenir l'URL
            String imageUrl = saveImage(image);

            // Créer l'objet Post et définir les attributs
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setType(PostTypeEnum.valueOf(type.toUpperCase()));
            post.setImageUrl(imageUrl);
            post.setUserId(userId);
            post.setLatitude(latitude);
            post.setLongitude(longitude);

            // Ajouter le post dans la base de données
            Post savedPost = postService.addPost(post, userId);

            return ResponseEntity.ok(savedPost);

        } catch (IOException e) {
            // Gestion des erreurs
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (IllegalArgumentException e) {
            // Mauvais type pour le PostTypeEnum
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Mettre à jour un post (avec nouvelle image optionnelle, latitude et longitude)
     */
    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long userId,
            @PathVariable Long postId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("type") String type,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {

        try {
            Post post = postService.retrievePost(postId);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            post.setTitle(title);
            post.setContent(content);
            post.setType(PostTypeEnum.valueOf(type.toUpperCase()));
            post.setLatitude(latitude);
            post.setLongitude(longitude);
            post.setUserId(userId);

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                post.setImageUrl(imageUrl);
            }

            Post updatedPost = postService.addPost(post, userId);
            return ResponseEntity.ok(updatedPost);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Liker un post
     */
    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable Long id, @PathVariable Long userId) {
        postService.likePost(id, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Supprimer un post et envoyer un email de suppression
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestParam String title,
<<<<<<< HEAD
            @RequestParam String firstName,
            @RequestParam String email) {

        Post post = postService.retrievePost(id);
        if (post != null) {
            postService.deletePost(id, title, firstName, email);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
=======
            @RequestParam String author,
            @RequestParam String email
    ) {
        postService.deletePost(id, title, author, email);
        return ResponseEntity.ok().build();
>>>>>>> c3d9507ad79d261ff9347f2c2b56b58830a98c30
    }




    @DeleteMapping("/delete-without-mail/{id}")
    public ResponseEntity<Void> deletePostWithouMail(@PathVariable Long id) {
        Post post = postService.retrievePost(id);
        if (post != null) {
            // Delete the post
            postService.deletePostWithouMail(id);


            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }




    /**
     * Sauvegarder une image sur le serveur et retourner son chemin
     */
    private String saveImage(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        Path uploadPath = Paths.get(System.getProperty("user.dir"), UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        image.transferTo(filePath.toFile());
        return fileName;
    }
}
