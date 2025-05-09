package tn.esprit.payment.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.payment.Dto.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8084/api/user")
public interface UserClient {

    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}