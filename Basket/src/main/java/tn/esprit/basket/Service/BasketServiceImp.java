package tn.esprit.basket.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.basket.Entity.Basket;
import tn.esprit.basket.Repository.BasketRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImp implements IBasketService {

    private final BasketRepository basketRepository;


    // Créer un panier
    @Override
    public Basket createBasket(Basket basket) {
        basket.setDateCreation(java.time.LocalDate.now());
        basket.setStatut("en cours");
        return basketRepository.save(basket);
    }

    // Récupérer tous les paniers d'un utilisateur
    @Override
    public List<Basket> getBasketsByUserId(Long userId) {
        return basketRepository.findByUserId(userId);
    }

    // Récupérer un panier par ID
    @Override
    public Optional<Basket> getBasketById(Long id) {
        return basketRepository.findById(id);
    }

    // Mettre à jour un panier
    @Override
    public Basket updateBasket(Long id, Basket basket) {
        Optional<Basket> existingBasket = basketRepository.findById(id);
        if (existingBasket.isPresent()) {
            Basket updatedBasket = existingBasket.get();
            updatedBasket.setStatut(basket.getStatut());
            updatedBasket.setTotal(basket.getTotal());
            updatedBasket.setModePaiement(basket.getModePaiement());
            updatedBasket.setDateModification(java.time.LocalDate.now());
            return basketRepository.save(updatedBasket);
        }
        return null; // Si panier non trouvé
    }

    // Supprimer un panier
    @Override
    public boolean deleteBasket(Long id) {
        Optional<Basket> existingBasket = basketRepository.findById(id);
        if (existingBasket.isPresent()) {
            basketRepository.delete(existingBasket.get());
            return true;
        }
        return false; // Si panier non trouvé
    }
}