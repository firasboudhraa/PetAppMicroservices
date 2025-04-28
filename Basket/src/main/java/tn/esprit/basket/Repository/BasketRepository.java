package tn.esprit.basket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.basket.Entity.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUserId(Long userId);
    Optional<Basket> findById(Long id);
}