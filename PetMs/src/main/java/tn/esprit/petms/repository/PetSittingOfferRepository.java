package tn.esprit.petms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.petms.entity.PetSittingOffer;

import java.util.List;

@Repository
public interface PetSittingOfferRepository extends JpaRepository<PetSittingOffer,Long> {
    List<PetSittingOffer> findAllByUserRequestStatusesIsNotEmpty() ;
}
