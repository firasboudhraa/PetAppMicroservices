package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.PetSittingOffer;
import tn.esprit.petms.entity.UserRequestStatus;
import tn.esprit.petms.repository.PetSittingOfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetSittingOfferServiceImpl implements IPetSittingOfferService {
   @Autowired
    PetSittingOfferRepository petSittingOfferRepository ;
    @Autowired
   NotificationService notificationService ;
    public PetSittingOffer savePetSittingOffer(PetSittingOffer request) {
        return petSittingOfferRepository.save(request);
    }
    public List<PetSittingOffer> getOfferMadeByUser(long userId){
        return petSittingOfferRepository.findAll().stream().filter(o -> o.getPet().getOwnerId() == userId).toList();
    }
    public List<PetSittingOffer> getAllPetSittingOffers(){
        return petSittingOfferRepository.findAll() ;
    }
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(Long userId) {
        return petSittingOfferRepository.findAll().stream()
                .filter(offer -> offer.getPet() != null && !offer.getPet().getOwnerId().equals(userId))
                .collect(Collectors.toList());
    }
    public List<PetSittingOffer> getReceivedPetSittingRequest(Long petOwnerId){
        return petSittingOfferRepository.findAll().stream().filter(o -> (o.getPet().getOwnerId() == petOwnerId))
                .filter(o -> !o.getUserRequestStatuses().isEmpty()).toList();
    }
    public List<PetSittingOffer> getSentPetSittingRequest(Long userId){
        var offer = petSittingOfferRepository.findAll().stream()
                .filter(o -> o.getUserRequestStatuses().stream()
                        .anyMatch(status -> status.getUserId() == userId)).toList();
        offer.forEach(o -> {
            o.getUserRequestStatuses().removeIf(status -> status.getUserId() != userId);
        });
        return offer ;
    }
    public boolean deleteoffer(long offerId){
        try {
            petSittingOfferRepository.deleteById(offerId);
            return true ;
        }catch (Exception e){
            return false ;
        }
    }

    public PetSittingOffer requestPetSittingOffer(long offerId, long userId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));

        boolean alreadyRequested = offer.getUserRequestStatuses().stream()
                .anyMatch(status -> status.getUserId().equals(userId));

        if (!alreadyRequested) {
            offer.getUserRequestStatuses().add(new UserRequestStatus(userId, "PENDING"));
            notificationService.sendAdoptionNotification(String.valueOf(offer.getPet().getOwnerId()), "New pet sitting request for your pet  "+offer.getPet().getName() +"!" );
            return petSittingOfferRepository.save(offer);
        }
        return null ;
    }

    public PetSittingOffer confirmPetSitter(long offerId, long sitterId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));
        offer.getUserRequestStatuses().forEach(status -> {
            status.setStatus( status.getUserId() == sitterId ? "CONFIRMED" : "REJECTED");
            if (status.getUserId() == sitterId) {
                notificationService.sendAdoptionNotification(String.valueOf(status.getUserId()), "Your pet sitting request has been accepted for pet "+offer.getPet().getName() +"!" );
            } else {
                notificationService.sendAdoptionNotification(String.valueOf(status.getUserId()), "Your pet sitting request has been rejected for pet "+offer.getPet().getName() +"! Because it has been accepted for another user !" );
            }
        });


        return petSittingOfferRepository.save(offer);
    }
    public PetSittingOffer rejectPetSitter(long offerId, long sitterId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));
        offer.getUserRequestStatuses().forEach(status -> {
            status.setStatus(status.getUserId() == sitterId ? "REJECTED" : status.getStatus());
        });
        notificationService.sendAdoptionNotification(String.valueOf(sitterId), "Your pet sitting request has been rejected for pet "+offer.getPet().getName() +"!" );
        return petSittingOfferRepository.save(offer);
    }
    public PetSittingOffer cancelPetSittingRequest(long offerId, long userId) {
        PetSittingOffer offer = petSittingOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));
        offer.getUserRequestStatuses().removeIf(status -> status.getUserId() == userId);
        return petSittingOfferRepository.save(offer);
    }

}
