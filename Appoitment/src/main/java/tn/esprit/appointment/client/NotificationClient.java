package tn.esprit.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.appointment.dto.NotificationDTO;

@FeignClient(name = "notification-ms", url = "${application.config.notifications-url}")
public interface NotificationClient {

    @PostMapping("/send")
    ResponseEntity<Void> sendNotification(@RequestBody NotificationDTO notification);

}
