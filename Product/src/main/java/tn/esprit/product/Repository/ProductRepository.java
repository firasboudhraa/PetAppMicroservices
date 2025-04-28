package tn.esprit.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.product.Entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByMarketplaceId(Long marketplaceId);
    void deleteByMarketplaceId(@Param("marketplaceId") Long marketplaceId);
    List<Product> findByUserId(Long userId);
}