package tn.esprit.petms.service;

import tn.esprit.petms.entity.AdoptionRequest;

import java.util.List;

public interface IAdoptionRequestService {
    public AdoptionRequest saveAdoptionRequest(AdoptionRequest request) ;
  //  public List<AdoptionRequest> getAllAdoptionRequestForOwner(Long petOwnerId) ;
    public List<AdoptionRequest> getAllAdoptionRequestByThisUser(Long requesterUserId) ;
    public void deleteAdoptionRequest(Long requestId) ;

    }
