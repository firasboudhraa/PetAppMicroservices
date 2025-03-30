package tn.esprit.medicalnotebook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @JsonProperty
    private Date DateTime;
    @JsonProperty
    private String description;
    @JsonProperty
    private Date next_due_date;
    }
