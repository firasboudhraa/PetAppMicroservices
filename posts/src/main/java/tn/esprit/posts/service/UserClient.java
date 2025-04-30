package tn.esprit.posts.service;

import tn.esprit.posts.entity.UserDTO;  // Your DTO class to represent the user response
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/user/retrieve-user/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
