package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.Pet;
import tn.esprit.petms.entity.PetSittingOffer;
import tn.esprit.petms.entity.UserRequestStatus;
import tn.esprit.petms.repository.PetSittingOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetSittingOfferServiceImpl implements IPetSittingOfferService {
   @Autowired
    PetSittingOfferRepository petSittingOfferRepository ;
    public PetSittingOffer savePetSittingOffer(PetSittingOffer request) {
        return petSittingOfferRepository.save(request);
    }

    public List<PetSittingOffer> getAllPetSittingOffers(){
        return petSittingOfferRepository.findAll() ;
    }
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(Long userId) {
        return petSittingOfferRepository.findAll().stream()
                .filter(offer -> offer.getPet() != null && !offer.getPet().getOwnerId().equals(userId))
                .collect(Collectors.toList());
    }
    public PetSittingOffer requestPetSittingOffer(long offerId, long userId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));

        boolean alreadyRequested = offer.getUserRequestStatuses().stream()
                .anyMatch(status -> status.getUserId().equals(userId));

        if (!alreadyRequested) {
            offer.getUserRequestStatuses().add(new UserRequestStatus(userId, "PENDING"));
            return petSittingOfferRepository.save(offer);
        }
        return null ;
    }

    public PetSittingOffer confirmPetSitter(long offerId, long sitterId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));

        offer.setUserRequestStatuses(
                offer.getUserRequestStatuses().stream()
                        .map(status -> {
                            if (sitterId == status.getUserId()) {
                                status.setStatus("CONFIRMED");
                            }
                            return status;
                        })
                        .toList()
        );

       return petSittingOfferRepository.save(offer);
    }


}
