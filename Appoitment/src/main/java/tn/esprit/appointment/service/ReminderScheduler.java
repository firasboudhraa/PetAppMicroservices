package tn.esprit.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.appointment.entity.Appointment;
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

    private Set<Long> sentReminderAppointments = new HashSet<>();


    @Scheduled(cron = "0 0 * * * *") // every minute
    public void sendReminders(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusHours(24);
        logger.info("Sending reminders for appointments between " + now + " and " + targetTime);

        List<Appointment> appointments = appointmentRepository.findByDateAppointmentBetween(now, targetTime);

        logger.info("Found " + appointments.size() + " appointments to send reminders for.");
        for(Appointment appointment : appointments) {
            if (!sentReminderAppointments.contains(appointment.getIdAppointment())) {
                logger.info("Reminder  sent for appointment ID: " + appointment.getIdAppointment());
                sendEmailReminder("firassbdh@gmail.com", appointment);
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
