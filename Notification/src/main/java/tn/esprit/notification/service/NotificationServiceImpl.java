package tn.esprit.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.notification.entity.Notification;
import tn.esprit.notification.repository.NotificationRepository;

import java.util.List;
@Service
public class NotificationServiceImpl implements  INotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        notification.setRead(false);
        return notificationRepository.save(notification);
    }
    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void markAllAsRead() {
        List<Notification> notifications = notificationRepository.findByIsReadFalse();
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }



}
