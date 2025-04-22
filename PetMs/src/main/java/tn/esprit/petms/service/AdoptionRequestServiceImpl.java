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
    @Autowired
    NotificationService notificationService ;
    public AdoptionRequest saveAdoptionRequest(AdoptionRequest request) {
        notificationService.sendAdoptionNotification(String.valueOf(request.getAdoptedPet().getOwnerId()), "New adoption request for your pet  "+request.getAdoptedPet().getName() +"!" );
        return repository.save(request);
    }

    public AdoptionRequest findByIdRequestAdoption(Long requestId) {
        return repository.findById(requestId).get();
    }


    public List<AdoptionRequest> getAllAdoptionRequestByThisUser(Long requesterUserId) {
        return repository.findAllByRequesterUserId( requesterUserId);
    }
    public List<AdoptionRequest> getAllAdoptionRequestSentToPetOwner(Long ownerId) {
        return repository.findByAdoptedPetOwnerId(ownerId);
    }

    public void deleteAdoptionRequest(Long requestId){
        repository.deleteById(requestId);
    }
    public AdoptionRequest confirmReques(Long requestId) {
        AdoptionRequest adpReq = repository.findById(requestId).get();
        adpReq.setIsConfirmed(true);
        notificationService.sendAdoptionNotification(String.valueOf(adpReq.getRequesterUserId()), "Your adoption request for pet  "+adpReq.getAdoptedPet().getName() +" has been confirmed!" );
        return repository.save(adpReq) ;

    }


    public AdoptionRequest rejectRequest(Long requestId,String reason) {
        AdoptionRequest adpReq = repository.findById(requestId).get();
        adpReq.setIsRejected(true);
        adpReq.setRejectionReason(reason);
        notificationService.sendAdoptionNotification(String.valueOf(adpReq.getRequesterUserId()), "Your adoption request for pet  "+adpReq.getAdoptedPet().getName() +" has been rejected!" );
        return repository.save(adpReq) ;

    }
}
