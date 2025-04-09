package tn.esprit.medicalnotebook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullCarnetResponse {
    @JsonProperty
    private List<MedicalRecord> medicalRecords;
    @JsonProperty
    private String name;
}
