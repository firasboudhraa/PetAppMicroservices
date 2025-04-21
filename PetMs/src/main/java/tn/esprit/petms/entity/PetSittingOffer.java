package tn.esprit.petms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetSittingOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate ;
    private String offerType ;
    private Long  amountPerDay ;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserRequestStatus> userRequestStatuses = new ArrayList<>();


    @JsonIgnoreProperties("petSittingOffers")
    @ManyToOne
    private Pet pet ;

}
