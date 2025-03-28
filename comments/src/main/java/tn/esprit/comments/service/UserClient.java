package tn.esprit.comments.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.comments.entity.UserDTO;

@FeignClient(name = "user", path = "/users")
public interface UserClient {
    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
