package tn.esprit.petservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private String name;
    private String species;
    private String imagePath;
    private Long ownerId ;
}
