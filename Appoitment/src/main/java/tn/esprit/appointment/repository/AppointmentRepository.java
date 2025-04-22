package tn.esprit.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.entity.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {
    List<Appointment> findByIdService(Long idService);

    void deleteByIdService(Long idService);

    List<Appointment> findByIdOwner(Long idOwner);

    List<Appointment> findByIdVet(Long idVet);

    List<Appointment> findByIdPet(Long idPet);

    List<Appointment> findByDateAppointmentBetween(LocalDateTime now, LocalDateTime targetTime);
}

