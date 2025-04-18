package tn.esprit.petms.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.PetSittingOffer;
import tn.esprit.petms.service.PetSittingOfferServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/petSittingOffer")

public class PetSittingOfferRestController {
    @Autowired
    PetSittingOfferServiceImpl petSittingOfferService ;

    @PostMapping
    public PetSittingOffer saveAdoptionRequest(@RequestBody PetSittingOffer petSittingOffer) {
        return petSittingOfferService.savePetSittingOffer(petSittingOffer);
    }
    @GetMapping
    public List<PetSittingOffer> getAllPetSittingOffers() {
        return petSittingOfferService.getAllPetSittingOffers();
    }

    @GetMapping("AvailableOffers/{userId}")
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(@PathVariable("userId") Long userId) {
        return petSittingOfferService.getAllAvailablePetSittingOffers(userId);
    }
}
