package tn.esprit.notificationms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import tn.esprit.notificationms.entity.Notification;
import tn.esprit.notificationms.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        notification.setSeen(false);
        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/notifications/" + notification.getReceiverId(), notification);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notifications/{receiverId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String receiverId) {
        return ResponseEntity.ok(notificationRepository.findByReceiverId(receiverId).stream().filter(n -> !n.isSeen()).toList());
    }
    @PutMapping("/notifications/seen/{id}")
    public ResponseEntity<Void> markAsSeen(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setSeen(true);
            notificationRepository.save(notification);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
