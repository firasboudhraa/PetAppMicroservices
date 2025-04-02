package tn.esprit.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.appointment.entity.Appointment;

import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {
    List<Appointment> findByIdService(Long idService);

    void deleteByIdService(Long idService);
}
