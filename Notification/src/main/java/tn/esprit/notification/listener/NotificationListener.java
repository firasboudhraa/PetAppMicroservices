package tn.esprit.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.notification.entity.Notification;
import tn.esprit.notification.entity.NotificationType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import tn.esprit.notification.service.NotificationServiceImpl;

import java.time.LocalDateTime;

@Component
public class NotificationListener {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "appointment.queue")
    @Transactional
    public void handleAppointmentNotification(@Payload String message) {
        System.out.println("Message received from RabbitMQ: " + message);
            Notification notification = new Notification();
            if (message.contains("created")) {
                notification.setType(NotificationType.valueOf("APPOINTMENT_CREATED"));
            } else if (message.contains("updated")) {
                notification.setType(NotificationType.valueOf("APPOINTMENT_UPDATED"));
            } else if (message.contains("deleted")) {
                notification.setType(NotificationType.valueOf("APPOINTMENT_DELETED"));
            } else if (message.contains("reminder")) {
                notification.setType(NotificationType.valueOf("APPOINTMENT_REMINDER"));
            }
            notification.setMessage(message);
            notification.setCreatedAt(LocalDateTime.now());
            notificationService.createNotification(notification);
            //send it with websocket
            messagingTemplate.convertAndSend("/topic/notifications", notification);

    }


    @RabbitListener(queues = "petservice.queue")
    @Transactional
    public void handlePetServiceNotification(@Payload String message) {
        System.out.println("Message received from RabbitMQ: " + message);

        Notification notification = new Notification();
        if (message.contains("created")) {
            notification.setType(NotificationType.valueOf("SERVICE_CREATED"));
        } else if (message.contains("updated")) {
            notification.setType(NotificationType.valueOf("SERVICE_UPDATED"));
        } else if (message.contains("deleted")) {
            notification.setType(NotificationType.valueOf("SERVICE_DELETED"));
        } else if (message.contains("confirmed")) {
            notification.setType(NotificationType.valueOf("APPOINTMENT_CONFIRMED"));
        } else if (message.contains("rejected")) {
            notification.setType(NotificationType.valueOf("APPOINTMENT_REJECTED"));
        }

        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notificationService.createNotification(notification);
        //send it with websocket
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }



}
