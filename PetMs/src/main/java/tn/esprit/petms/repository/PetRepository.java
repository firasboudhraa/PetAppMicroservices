package tn.esprit.petms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.petms.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
