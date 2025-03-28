package tn.esprit.medicalnotebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.medicalnotebook.entity.Carnet;

@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

}
