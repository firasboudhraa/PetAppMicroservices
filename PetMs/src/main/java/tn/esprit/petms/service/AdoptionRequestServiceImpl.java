package tn.esprit.petms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.Pet;
import tn.esprit.petms.repository.AdoptionRequestRepository;
import tn.esprit.petms.repository.PetRepository;

import java.util.List;

@Service
public class AdoptionRequestServiceImpl implements IAdoptionRequestService{
    @Autowired
    private AdoptionRequestRepository repository;
    @Autowired
    NotificationService notificationService ;
    @Autowired
    PetRepository petRepository ;
    public boolean tranfertPet(Long petId, Long newOwnerId , Long adoptionRequestId) {
        Pet pet = petRepository.findById(petId).orElse(null) ;
        AdoptionRequest adp = repository.findById(adoptionRequestId).orElse(null) ;
        if(pet != null){
            pet.setOwnerId(newOwnerId);
            pet.setForAdoption(false);
            petRepository.save(pet) ;
            if (adp != null) {
                adp.setIsTransfered(true);
                notificationService.sendPetTransferNotification(newOwnerId.toString(),"Congratulation for adopting your new "+pet.getSpecies()+" "+pet.getName()+" !Your pet info has been transferred ");
                repository.delete(adp);
                return true ;
            }
        }
        return false;
    }

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
        List<AdoptionRequest> allReq = repository.findAllByAdoptedPet(adpReq.getAdoptedPet()) ;
        allReq.stream().filter(r -> r.getId() != requestId).forEach(
                r -> {
                    r.setIsRejected(true);
                    r.setRejectionReason("Sorry the pet was adopted by another user");
                    notificationService.sendAdoptionNotification(r.getRequesterUserId().toString(),
                            "Sorry the "+r.getAdoptedPet().getSpecies()+" "+r.getAdoptedPet().getName()+"was adopted by another user");
                    repository.save(r) ;
                }
        );
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
