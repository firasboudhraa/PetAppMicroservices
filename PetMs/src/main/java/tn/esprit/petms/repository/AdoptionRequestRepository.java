package tn.esprit.petms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.petms.entity.AdoptionRequest;
import tn.esprit.petms.entity.Pet;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest,Long> {

    //List<AdoptionRequest> findAllByPetOwnerId(Long petOwnerId);
    List<AdoptionRequest> findAllByRequesterUserId(Long requesterUserId);
    List<AdoptionRequest> findByAdoptedPetOwnerId(Long ownerId);
    List<AdoptionRequest> findAllByAdoptedPet ( Pet adoptedPet) ;
}
