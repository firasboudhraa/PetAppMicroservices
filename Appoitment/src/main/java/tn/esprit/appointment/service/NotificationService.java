package tn.esprit.appointment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.appointment.client.NotificationClient;
import tn.esprit.appointment.dto.NotificationDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;

    public void sendAppointmentNotification(String receiverId,String msg,String senderId) {
        NotificationDTO notif = NotificationDTO.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .message(msg)
                .timestamp(LocalDateTime.now())
                .seen(false)
                .build();

        notificationClient.sendNotification(notif);
    }
}
