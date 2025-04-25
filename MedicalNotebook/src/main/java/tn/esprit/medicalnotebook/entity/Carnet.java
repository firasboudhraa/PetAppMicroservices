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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
     @JsonProperty
     private String name;
/*
   @Column(nullable = true)
     private Long PetId;*/
    /*@JsonProperty
    private String medicalHistory;*/
    /*@JsonProperty

    private Pet pet_id;
    @JsonProperty
    private List<MedicalRecord> medicalRecords;*/
}
