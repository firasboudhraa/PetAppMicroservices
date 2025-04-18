package tn.esprit.petms.service;

import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.PetSittingOffer;

import java.util.List;

public interface IPetSittingOfferService {
    public PetSittingOffer savePetSittingOffer (PetSittingOffer request) ;
    public List<PetSittingOffer> getAllPetSittingOffers();
    public List<PetSittingOffer> getAllAvailablePetSittingOffers(Long userId) ;

}
