package tn.esprit.notification.service;

import tn.esprit.notification.entity.Notification;

import java.util.List;

public interface INotificationService {
    Notification createNotification(Notification notification);
    List<Notification> getAllNotifications();
    void markAllAsRead();
}
