package tn.esprit.petms.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private int age;
    private String color ;
    private String sex ;
    private boolean forAdoption ;
    private String description ;
    private String location ;


    private Long ownerId ;
    private String imagePath;
    @JsonIgnoreProperties("adoptedPet")
    @OneToMany(mappedBy = "adoptedPet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AdoptionRequest> adoptionRequests;

    @JsonIgnoreProperties("pet")
    @OneToMany(mappedBy = "pet" , cascade = CascadeType.ALL)
    private List<PetSittingOffer> petSittingOffers ;

    public Pet(String name, String species, int age,
               String color, String sex,String description , String location
            , Long ownerId, String imagePath , boolean forAdoption, List<AdoptionRequest> adoptionRequests) {
        this.name = name ;
        this.species=species;
        this.age=age;
        this.color=color;
        this.description = description ;
        this.sex =sex;
        this.ownerId=ownerId;
        this.imagePath=imagePath;
        this.forAdoption=forAdoption ;
        this.location = location ;
        this.adoptionRequests=adoptionRequests ;

    }
//    public Pet() {
//    }
//
//    public Pet(Long id, String name, String species, int age, String color, String sex, Long ownerId, String imagePath) {
//        this.id = id;
//        this.name = name;
//        this.species = species;
//        this.age = age;
//        this.color = color;
//        this.sex = sex;
//        this.ownerId = ownerId;
//        this.imagePath = imagePath;
//    }
}

