package tn.esprit.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationListener {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "appointment.queue")
    @Transactional
    public void handleAppointmentNotification(@Payload String message) {
        System.out.println("Message received from RabbitMQ: " + message);

        try {
            // Extract details from the received message
            String email = extractValue(message, "email");
            String dateAppointment = extractValue(message, "dateAppointment");
            String reason = extractValue(message, "reason");

            // Construct content for the email
            String content = "Dear user,\n\nYour appointment is scheduled for " + dateAppointment +
                    ".\nReason: " + reason + "\n\nThank you.";

            System.out.println("Content: " + content);

            // Send the email
            sendEmail(email, "Appointment Confirmation", content);

        } catch (Exception e) {
            System.err.println("Failed to process appointment notification: " + e.getMessage());
        }
    }


    private String extractValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\"") + key.length() + 4;
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("fboudhraa2@gmail.com");  // Set the sender's email address
        mailSender.send(message);  // Send the email
    }
}
