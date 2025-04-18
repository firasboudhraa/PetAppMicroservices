package tn.esprit.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find notifications by type or user
    // List<Notification> findByType(NotificationType type);
    // List<Notification> findByUserId(Long userId);
}
