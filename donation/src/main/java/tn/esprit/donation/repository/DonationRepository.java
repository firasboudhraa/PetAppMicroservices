package tn.esprit.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.donation.entity.Donation;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository <Donation,Long> {
    List<Donation> findAllByEventId(Long eventId);
}
