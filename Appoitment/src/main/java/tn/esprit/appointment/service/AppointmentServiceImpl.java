package tn.esprit.appointment.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.entity.AppointmentStatus;
import tn.esprit.appointment.rabbitmq.RabbitMQMessageProducer;
import tn.esprit.appointment.repository.AppointmentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl  implements  IAppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    @Override
    @Transactional
    public Appointment addAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.PENDING);
        String email = "firassbdh@gmail.com";
        String message = String.format(
                "{ \"email\": \"%s\", \"dateAppointment\": \"%s\", \"reason\": \"%s\" }",
                email,
                appointment.getDateAppointment(),
                appointment.getReason()
        );

        rabbitMQMessageProducer.publish(
                message,
                "appointment.exchange",
                "appointment.routingkey"
        );
        System.out.println("Message sent to RabbitMQ: " + message);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).get();
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByService(Long idService) {
        return appointmentRepository.findByIdService(idService);
    }

    @Override
    @Transactional
    public void deleteAppointmentByService(Long idService) {
        appointmentRepository.deleteByIdService(idService);
    }

    @Override
    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status, String reason) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setStatus(status);
        appointment.setReason(reason);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByOwner(Long idOwner) {
        return appointmentRepository.findByIdOwner(idOwner);
    }

    @Override
    public List<Appointment> getAppointmentsByVet(Long idVet) {
        return appointmentRepository.findByIdVet(idVet);
    }

    @Override
    public List<Appointment> getAppointmentsByPet(Long idPet) {
        return appointmentRepository.findByIdPet(idPet);
    }
}
