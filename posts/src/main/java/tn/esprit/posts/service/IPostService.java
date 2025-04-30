package tn.esprit.posts.service;

import tn.esprit.posts.entity.Post;
import java.util.List;

public interface IPostService {

    List<Post> retrieveAllPosts();

    Post retrievePost(Long postId);

    Post addPost(Post post, Long userId);

    void likePost(Long postId, Long userId);

    void deletePost(Long postId, String title, String firstName, String email);

    public void deletePostWithouMail(Long postId) ;

    void reportPost(Long postId);
}
