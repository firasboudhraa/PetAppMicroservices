package tn.esprit.petservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
}
