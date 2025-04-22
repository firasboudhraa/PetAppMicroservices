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
            notification.setType(NotificationType.valueOf("APPOINTMENT_CREATED"));
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
            notification.setType(NotificationType.valueOf("SERVICE_CREATED"));
            notification.setMessage(message);
            notification.setCreatedAt(LocalDateTime.now());
            notificationService.createNotification(notification);
            //send it with websocket
            messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    @RabbitListener(queues = "appointment.queue")
    @Transactional
    public void handleAppointmentUpdateNotification(@Payload String message) {
        System.out.println("Message received from RabbitMQ: " + message);
            Notification notification = new Notification();
            notification.setType(NotificationType.valueOf("APPOINTMENT_UPDATED"));
            notification.setMessage(message);
            notification.setCreatedAt(LocalDateTime.now());
            notificationService.createNotification(notification);
            //send it with websocket
            messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    @RabbitListener(queues = "appointment.queue")
    @Transactional
    public void handleAppointmentDeleteNotification(@Payload String message) {
        System.out.println("Message received from RabbitMQ: " + message);
            Notification notification = new Notification();
            notification.setType(NotificationType.valueOf("APPOINTMENT_DELETED"));
            notification.setMessage(message);
            notification.setCreatedAt(LocalDateTime.now());
            notificationService.createNotification(notification);
            //send it with websocket
            messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

}
