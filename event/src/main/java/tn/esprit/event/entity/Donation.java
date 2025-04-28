package tn.esprit.event.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {
    @JsonProperty
    private float amount;
    @JsonProperty
    private LocalDateTime date;
    @JsonProperty
    private String status;
}
