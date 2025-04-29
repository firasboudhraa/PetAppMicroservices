package tn.esprit.dto.matching;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestDTO {
    private UserProfile user;
    private List<PetProfile> pets;
    private int top_n;

    @Data
    @NoArgsConstructor
    public static class UserProfile {
        private String id;
        private String lifestyle;
        private String experience;
        private String living_space;
        private String preferences;
    }

    @Data
    @NoArgsConstructor
    public static class PetProfile {
        private String id;
        private String name;
        private String species;
        private int age;
        private String color;
        private String sex;
        private String description;
        private boolean forAdoption;
    }
}