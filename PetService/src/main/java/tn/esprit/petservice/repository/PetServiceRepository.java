package tn.esprit.petservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.petservice.entity.PetService;

import java.util.List;

public interface PetServiceRepository extends JpaRepository<PetService, Long> {
    List<PetService> findByProviderId(Long providerId);
}
