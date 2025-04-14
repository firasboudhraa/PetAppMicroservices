package tn.esprit.appointment.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;
    private LocalDateTime dateAppointment;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private String reason;
    private Long idVet;
    private Long idPet;
    private Long idOwner;
    private Long idService;
}
