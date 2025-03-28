package tn.esprit.medicalrecord.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long UUId;
    @JsonProperty
    private Date DateTime;
    /*@JsonProperty
    private MedicalRecordType type;*/
    @JsonProperty
    private String description;
    @JsonProperty
    private long veterinarian_id;
    @JsonProperty
    private Date next_due_date;
    @JsonProperty
    @Column(nullable = true)
    private long carnet_id;
    @JsonProperty
    @ElementCollection
    private List<String> attachments = new ArrayList<>();
   /* @JsonProperty
    @ManyToOne
    private Carnet carnet;*/
}
