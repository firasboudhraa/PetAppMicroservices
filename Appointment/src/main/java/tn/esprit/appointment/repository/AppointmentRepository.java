package tn.esprit.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.appointment.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByServiceId(Long serviceId);
    List<Appointment> findByCustomerId(Long customerId);
    List<Appointment> findByVetId(Long vetId);
    List<Appointment> findByPetId(Long petId);
}
