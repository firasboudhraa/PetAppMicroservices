package tn.esprit.petservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.petservice.entity.PetService;

import java.util.List;
import java.util.Optional;

public interface PetServiceRepository extends JpaRepository<PetService, Long> {
    List<PetService> findByProviderId(Long providerId);

    @Query("SELECT s FROM PetService s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<PetService> findFirstByNameContainingIgnoreCase(@Param("name") String name);}
