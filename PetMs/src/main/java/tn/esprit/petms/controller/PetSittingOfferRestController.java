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
    PetSittingOfferServiceImpl petSittingOfferService;

    @PostMapping
    public PetSittingOffer saveAdoptionRequest(@RequestBody PetSittingOffer petSittingOffer) {
        return petSittingOfferService.savePetSittingOffer(petSittingOffer);
    }
    @GetMapping("getAllMadeByUser/{userId}")
    public List<PetSittingOffer> getOfferMadeByUser(@PathVariable("userId") long userId) {
        return petSittingOfferService.getOfferMadeByUser(userId) ;
    }
        @GetMapping
    public List<PetSittingOffer> getAllPetSittingOffers() {
        return petSittingOfferService.getAllPetSittingOffers();
    }
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable("id") long id ){
        return petSittingOfferService.delete(id);
    }

    @GetMapping("AvailableOffers/{userId}")
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(@PathVariable("userId") Long userId) {
        return petSittingOfferService.getAllAvailablePetSittingOffers(userId);
    }

    @PostMapping("/{offerId}/request/{sitterId}")
    public PetSittingOffer requestPetSittingOffer(@PathVariable Long offerId, @PathVariable Long sitterId) {
        return petSittingOfferService.requestPetSittingOffer(offerId, sitterId);
    }

    @PutMapping("/{offerId}/confirm/{sitterId}")
    public PetSittingOffer confirmPetSitter(@PathVariable("offerId") Long offerId, @PathVariable("sitterId") Long sitterId) {
        return petSittingOfferService.confirmPetSitter(offerId, sitterId);
    }

    @GetMapping("/receivedRequests/{userId}")
    public List<PetSittingOffer> getReceivedPetSittingRequest(@PathVariable("userId") Long userId) {
        return petSittingOfferService.getReceivedPetSittingRequest(userId);
    }

    @GetMapping("/sentRequests/{userId}")
    public List<PetSittingOffer> getSentPetSittingRequest(@PathVariable("userId") Long userId) {
        return petSittingOfferService.getSentPetSittingRequest(userId);
    }

    @DeleteMapping("/{offerId}/reject/{sitterId}")
    public PetSittingOffer rejectPetSitter(@PathVariable("offerId") Long offerId, @PathVariable("sitterId") Long sitterId) {
        return petSittingOfferService.rejectPetSitter(offerId, sitterId);
    }
    @DeleteMapping("/{offerId}/cancel/{sitterId}")
    public PetSittingOffer cancelPetSittingRequest(@PathVariable("offerId") Long offerId, @PathVariable("sitterId") Long sitterId) {
        return petSittingOfferService.cancelPetSittingRequest(offerId, sitterId);
    }
}