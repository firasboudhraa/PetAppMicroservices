package tn.esprit.marketplace.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.marketplace.Entity.Marketplace;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
}