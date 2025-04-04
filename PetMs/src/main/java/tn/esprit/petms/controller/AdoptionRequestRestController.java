package tn.esprit.petms.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.service.AdoptionRequestServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/adoptionRequest")
public class AdoptionRequestRestController {
    @Autowired
    private AdoptionRequestServiceImpl adoptionRequestService;

    // Endpoint to save a new adoption request
    @PostMapping
    public AdoptionRequest saveAdoptionRequest(@RequestBody AdoptionRequest adoptionRequest) {
        return adoptionRequestService.saveAdoptionRequest(adoptionRequest);
    }

    // Endpoint to get all adoption requests for a specific pet owner
   /* @GetMapping("/owner/{petOwnerId}")
    public List<AdoptionRequest> getAllAdoptionRequestsForOwner(@PathVariable Long petOwnerId) {
        return adoptionRequestService.getAllAdoptionRequestForOwner(petOwnerId);
    }*/
    @GetMapping("/requester/{requesterUserId}")
    public List<AdoptionRequest> getAllAdoptionRequestByThisUser(@PathVariable Long requesterUserId) {
        return adoptionRequestService.getAllAdoptionRequestByThisUser(requesterUserId);
    }


}
