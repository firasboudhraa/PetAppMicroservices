package tn.esprit.notification.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.notification.entity.Notification;
import tn.esprit.notification.service.INotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @GetMapping("/all-notifications")
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        for (Notification notification : notifications) {
            notification.setMessage(notification.getMessage().replace("\"", ""));
        }
        return notifications;
    }


    @PutMapping("/read-all")
    public void markAllAsRead() {
        notificationService.markAllAsRead();
    }
}
