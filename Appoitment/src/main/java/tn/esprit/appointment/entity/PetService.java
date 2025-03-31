package tn.esprit.appointment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetService {

    private String name;
    private String description;
    private Float price;
    private int durationInMinutes;
    private String address;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    List<LocalDateTime>  availableSlots;
}
