package tn.esprit.petms.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.Pet;
import tn.esprit.petms.service.AdoptionRequestServiceImpl;
import tn.esprit.petms.service.NotificationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/adoptionRequest")
public class AdoptionRequestRestController {
    @Autowired
    private AdoptionRequestServiceImpl adoptionRequestService;
    @Autowired
    NotificationService notificationService ;
    // Endpoint to save a new adoption request
    @PostMapping
    public AdoptionRequest saveAdoptionRequest(@RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.saveAdoptionRequest(adoptionRequest);
    }
    @PutMapping("/transferPet/{adoptionRequestId}/{petId}/{newOwnerId}")
    public boolean tranferPet(@PathVariable("petId") long petId , @PathVariable("newOwnerId") long newOwnerId,@PathVariable("adoptionRequestId") long adoptionRequestId) {

        return adoptionRequestService.tranfertPet(petId,newOwnerId,adoptionRequestId) ;
    }

    @GetMapping("/requester/{requesterUserId}")
    public List<AdoptionRequest> getAllAdoptionRequestByThisUser(@PathVariable Long requesterUserId) {
        return adoptionRequestService.getAllAdoptionRequestByThisUser(requesterUserId);
    }
    @PutMapping
    public AdoptionRequest editAdoptionRequest(@RequestBody AdoptionRequest request) {
        if(request.getIsChangedByRequestOwner()){
            notificationService.sendAdoptionNotification(String.valueOf(request.getAdoptedPet().getOwnerId()), "The adopter has requested some changes to the adoption request for your pet "+request.getAdoptedPet().getName()  );
        } else if (request.getIsChangedByPetOwner()){
            notificationService.sendAdoptionNotification(String.valueOf(request.getRequesterUserId()), "The pet owner has requested some changes to the adoption request for the pet "+request.getAdoptedPet().getName()  );

        }
        return adoptionRequestService.saveAdoptionRequest(request);
    }


    @GetMapping("/{requestId}")
    public AdoptionRequest getAdoptionRequestById(@PathVariable Long requestId) {
        return adoptionRequestService.findByIdRequestAdoption(requestId);
    }
    @DeleteMapping("/{adoptionRequestId}")
    public void deleteAdoptionRequest(@PathVariable("adoptionRequestId") Long adoptionRequestId){
        adoptionRequestService.deleteAdoptionRequest(adoptionRequestId);
    }
    @GetMapping("/owner/{ownerId}")
    public List<AdoptionRequest> getAllAdoptionRequestSentToPetOwner(@PathVariable Long ownerId) {
        return adoptionRequestService.getAllAdoptionRequestSentToPetOwner(ownerId);
    }

    @PutMapping("/confirm/{adoptionRequestId}")
    public AdoptionRequest confirmRequest(@PathVariable("adoptionRequestId") Long adoptionRequestId) {
        return adoptionRequestService.confirmReques(adoptionRequestId);
    }

    @PutMapping("/reject/{requestId}")
    public AdoptionRequest rejectRequest(@PathVariable("requestId") Long requestId , @RequestBody String reason){
        return adoptionRequestService.rejectRequest(requestId,reason ) ;
    }

}
