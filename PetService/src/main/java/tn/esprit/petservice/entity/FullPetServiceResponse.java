package tn.esprit.petservice.entity;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullPetServiceResponse {

    private String name;
    private String description;
    private Float price;
    private int durationInMinutes;
    private String address;

    private List<Appointment> appointments;
}
