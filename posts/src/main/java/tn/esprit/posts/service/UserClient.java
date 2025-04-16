package tn.esprit.posts.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.posts.entity.UserDTO;

@FeignClient(name = "user", path = "/user")
public interface UserClient {
    @GetMapping("/retrieve-user/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
