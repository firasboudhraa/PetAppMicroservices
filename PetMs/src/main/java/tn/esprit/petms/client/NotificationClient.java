package tn.esprit.petms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.petms.entity.NotificationDTO;

@FeignClient(name = "notification-ms", url = "http://localhost:8055") // ou URL Eureka si tu l’utilises
public interface NotificationClient {

    @PostMapping("/send")
    ResponseEntity<Void> sendNotification(@RequestBody NotificationDTO notification);
}