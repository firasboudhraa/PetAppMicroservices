package tn.esprit.petms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("adoptionRequests")
    @ManyToOne
    private Pet adoptedPet;

    private Long requesterUserId;

    private String location;
    private String message;
    private Boolean isConfirmed ;
    private Boolean isChangedByPetOwner ;
    private Boolean isChangedByRequestOwner ;

    private LocalDate date;
    private LocalTime time;
}
