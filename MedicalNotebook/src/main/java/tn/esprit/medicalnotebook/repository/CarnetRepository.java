package tn.esprit.medicalnotebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.medicalnotebook.entity.Carnet;
import tn.esprit.medicalnotebook.entity.FullCarnetResponse;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {
}
