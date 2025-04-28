package tn.esprit.posts.service;

import tn.esprit.posts.entity.Post;

import java.util.List;

public interface IPostService {
    List<Post> retrieveAllPosts();
    Post retrievePost(Long postId);
    Post addPost(Post post, Long userId);
    void likePost(Long postId, Long userId);
<<<<<<< HEAD
    public void deletePost(Long postId, String title, String firstName, String email);
=======


    void deletePost(Long postId, String title, String author, String email);

>>>>>>> c3d9507ad79d261ff9347f2c2b56b58830a98c30
    void deletePostWithouMail(Long postId);
}