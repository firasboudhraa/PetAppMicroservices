package tn.esprit.appoitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.appoitment.entity.Appointment;

public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {
}
