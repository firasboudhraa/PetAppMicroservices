package tn.esprit.petms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.petms.client.NotificationClient;
import tn.esprit.petms.entity.NotificationDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;

    public void sendAdoptionNotification(String receiverId,String msg) {
        NotificationDTO notif = NotificationDTO.builder()
                .senderId("pet-service")
                .receiverId(receiverId)
                .message(msg)
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();

        notificationClient.sendNotification(notif);
    }
}
