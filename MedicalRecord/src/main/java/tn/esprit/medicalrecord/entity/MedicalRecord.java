package tn.esprit.medicalrecord.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonProperty
    private Date dateTime;
    @JsonProperty
    private MedicalRecordType type;
    @JsonProperty
    private String description;
    @JsonProperty
    private long owner_id;
    @JsonProperty
    private Date next_due_date;
    @JsonProperty
    private long carnetId;

}
