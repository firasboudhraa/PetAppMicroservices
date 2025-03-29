package tn.esprit.petms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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


    private Long ownerId ;
    private String imagePath;


    public Pet(String name, String species, int age, String color, String sex, Long ownerId, String imagePath) {
        this.name = name ;
        this.species=species;
        this.age=age;
        this.color=color;
        this.sex =sex;
        this.ownerId=ownerId;
        this.imagePath=imagePath;

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

