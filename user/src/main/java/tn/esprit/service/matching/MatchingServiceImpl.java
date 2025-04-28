package tn.esprit.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.client.PetServiceClient;
import tn.esprit.dto.matching.MatchRequestDTO;
import tn.esprit.dto.matching.MatchResponseDTO;
import tn.esprit.service.user.IUserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements IMatchingService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final IUserService userService;
    private final PetServiceClient petServiceClient;

    @Value("${matching.service.url:http://localhost:8020}")
    private String matchingServiceUrl;

    @Override
    public MatchResponseDTO matchPetsToUser(Long userId, Integer topN) {
        // Get user adoption preferences
        Map<String, String> preferences = userService.getAdoptionPreferences(userId);
        if (preferences.isEmpty()) {
            throw new RuntimeException("User has no adoption preferences set");
        }

        // Get available pets
        List<MatchRequestDTO.PetProfile> allPets = petServiceClient.getPets();
        List<MatchRequestDTO.PetProfile> petsForAdoption = allPets.stream()
                .filter(MatchRequestDTO.PetProfile::isForAdoption)
                .collect(Collectors.toList());

        // Create match request with userId passed as a parameter
        MatchRequestDTO matchRequest = createMatchRequest(preferences, petsForAdoption, topN, userId);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<MatchRequestDTO> request = new HttpEntity<>(matchRequest, headers);

        // Call FastAPI service
        return restTemplate.postForObject(
                matchingServiceUrl + "/api/match",
                request,
                MatchResponseDTO.class);
    }

    private MatchRequestDTO createMatchRequest(Map<String, String> preferences, List<MatchRequestDTO.PetProfile> pets, Integer topN, Long userId) {
        MatchRequestDTO.UserProfile userProfile = new MatchRequestDTO.UserProfile();

        // Set the user ID
        userProfile.setId(userId.toString());

        userProfile.setLifestyle(preferences.getOrDefault("lifestyle", ""));
        userProfile.setExperience(preferences.getOrDefault("experience", ""));
        userProfile.setLiving_space(preferences.getOrDefault("living_space", preferences.getOrDefault("livingSpace", "")));
        userProfile.setPreferences(preferences.getOrDefault("preferences", ""));

        List<MatchRequestDTO.PetProfile> petProfiles = pets.stream()
                .map(pet -> {
                    MatchRequestDTO.PetProfile profile = new MatchRequestDTO.PetProfile();
                    profile.setId(pet.getId().toString());
                    profile.setName(pet.getName());
                    profile.setSpecies(pet.getSpecies());
                    profile.setAge(pet.getAge());
                    profile.setColor(pet.getColor());
                    profile.setSex(pet.getSex());
                    profile.setDescription(pet.getDescription());
                    profile.setForAdoption(pet.isForAdoption());
                    return profile;
                })
                .toList();

        return new MatchRequestDTO(userProfile, petProfiles, topN != null ? topN : 3);
    }

}