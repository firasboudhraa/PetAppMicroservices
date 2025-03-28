package tn.esprit.medicalnotebook.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carnet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long UUId;
    /* @JsonProperty

   @Column(nullable = true)
     private Long PetId;*/
    @JsonProperty
    private String MedicalHistory;
    /* @JsonProperty
    @OneToOne
     private Pet pet;
    @JsonProperty
    private List<MedicalRecord> medicalRecords;*/
}
