package tn.esprit.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.appointment.client.UserClient;
import tn.esprit.appointment.dto.UserDTO;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.rabbitmq.RabbitMQMessageProducer;
import tn.esprit.appointment.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class ReminderScheduler {

    private static  final Logger logger = Logger.getLogger(ReminderScheduler.class.getName());
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserClient userClient;

    @Autowired
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    private Set<Long> sentReminderAppointments = new HashSet<>();

    public ReminderScheduler(RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }


    @Scheduled(cron = "0 0 * * * *")
    public void sendReminders(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusHours(24);

        List<Appointment> appointments = appointmentRepository.findByDateAppointmentBetween(now, targetTime);

        for(Appointment appointment : appointments) {
            if (!sentReminderAppointments.contains(appointment.getIdAppointment())) {
                Long idOwner = appointment.getIdOwner();
                UserDTO user = userClient.getUserById(idOwner);
                sendEmailReminder(user.getEmail(), appointment);
                String message = "Appointment Reminder";
                rabbitMQMessageProducer.publish(
                        message,
                        "appointment.exchange",
                        "appointment.routingkey"
                );
                sentReminderAppointments.add(appointment.getIdAppointment());
            }
        }
    }

    private void sendEmailReminder(String email, Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Appointment Reminder");
        message.setText("You have an appointment scheduled on " + appointment.getDateAppointment() + ".\n" +
                "Reason: " + appointment.getReason());
        try {
            mailSender.send(message);
            logger.info("Reminder sent to " + email + " for appointment ID: " + appointment.getIdAppointment());
        } catch (Exception e) {
            logger.severe("Failed to send reminder to " + email + " for appointment ID: " + appointment.getIdAppointment() + ". Error: " + e.getMessage());
        }
    }
}
