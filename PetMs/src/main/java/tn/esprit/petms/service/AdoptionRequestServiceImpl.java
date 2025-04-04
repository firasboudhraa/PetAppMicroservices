package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.repository.AdoptionRequestRepository;

import java.util.List;

@Service
public class AdoptionRequestServiceImpl {
    @Autowired
    private AdoptionRequestRepository repository;

    public AdoptionRequest saveAdoptionRequest(AdoptionRequest request) {
        return repository.save(request);
    }
    /*public List<AdoptionRequest> getAllAdoptionRequestForOwner(Long petOwnerId) {
        return repository.findAllByPetOwnerId( petOwnerId);
    }*/
    public List<AdoptionRequest> getAllAdoptionRequestByThisUser(Long requesterUserId) {
        return repository.findAllByRequesterUserId( requesterUserId);
    }
}
