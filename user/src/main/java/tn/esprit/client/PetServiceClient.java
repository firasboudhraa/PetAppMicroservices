package tn.esprit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tn.esprit.dto.matching.MatchRequestDTO;

import java.util.List;

@FeignClient(name = "pet-ms", url = "${application.config.pet-url}")
public interface PetServiceClient {

    @GetMapping("/api/v1/pet/retrieve-all-pets")
    List<MatchRequestDTO.PetProfile> getPets();
}
