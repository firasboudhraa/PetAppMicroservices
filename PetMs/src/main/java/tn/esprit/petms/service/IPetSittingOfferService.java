package tn.esprit.petms.service;

import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.PetSittingOffer;

import java.util.List;

public interface IPetSittingOfferService {
    public List<PetSittingOffer> getOfferMadeByUser(long userId);

        public PetSittingOffer savePetSittingOffer (PetSittingOffer request) ;
    public List<PetSittingOffer> getAllPetSittingOffers();
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(Long userId) ;
    public PetSittingOffer confirmPetSitter(long offerId , long sitterId);
    public PetSittingOffer requestPetSittingOffer(long offerId, long userId) ;
    public List<PetSittingOffer> getReceivedPetSittingRequest(Long petOwnerId) ;

    public List<PetSittingOffer> getSentPetSittingRequest(Long userId);
    public PetSittingOffer rejectPetSitter(long offerId, long sitterId) ;
    public PetSittingOffer cancelPetSittingRequest(long offerId, long userId) ;

    }
