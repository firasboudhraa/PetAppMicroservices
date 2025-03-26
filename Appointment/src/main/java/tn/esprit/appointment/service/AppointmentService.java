package tn.esprit.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.appointment.client.PetServiceClient;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AppointmentService  implements  IAppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;
    private PetServiceClient petServiceClient;


    @Override
    public Appointment createAppointment(Appointment appointment) {
        List<LocalDateTime> slots = petServiceClient.getAvailableSlots(appointment.getIdService());
        if (!slots.contains(appointment.getAppointmentDate())) {
            throw new RuntimeException("Slot not available");
        }
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
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).get();
    }

    @Override
    public List<Appointment> getAppointmentsByService(Long serviceId) {
        return appointmentRepository.findByServiceId(serviceId);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Appointment> getAppointmentsByVet(Long vetId) {
        return appointmentRepository.findByVetId(vetId);
    }

}
