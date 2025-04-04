package tn.esprit.petms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.petms.entity.AdoptionRequest;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest,Long> {

    //List<AdoptionRequest> findAllByPetOwnerId(Long petOwnerId);
    List<AdoptionRequest> findAllByRequesterUserId(Long requesterUserId);

}
