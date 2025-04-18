package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.PetSittingOffer;
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


}
