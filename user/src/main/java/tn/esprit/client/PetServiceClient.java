package tn.esprit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tn.esprit.dto.matching.MatchRequestDTO;

import java.util.List;

@FeignClient(name = "PetMs", url = "http://localhost:8050")
public interface PetServiceClient {

    @GetMapping("/api/v1/pet/retrieve-all-pets")
    List<MatchRequestDTO.PetProfile> getPets();
}
