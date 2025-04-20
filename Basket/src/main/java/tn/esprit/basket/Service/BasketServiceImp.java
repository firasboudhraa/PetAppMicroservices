package tn.esprit.basket.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.basket.Client.ProductClient;
import tn.esprit.basket.Entity.Basket;
import tn.esprit.basket.Repository.BasketRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImp implements IBasketService {

    private final BasketRepository basketRepository;
    private final ProductClient productClient;


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

    // Ajouter un produit au panier
    @Override
    public Basket addProductToBasket(Long basketId, Long productId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            basket.addProduct(productId);
            basket.syncListToProductIds();
            basket.setDateModification(LocalDate.now());
            return basketRepository.save(basket);
        }
        return null;
    }

    // Supprimer un produit du panier
    @Override
    public Basket removeProductFromBasket(Long basketId, Long productId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            basket.removeProduct(productId);
            basket.syncListToProductIds();
            basket.setDateModification(LocalDate.now());
            return basketRepository.save(basket);
        }
        return null;
    }

    // Valider un panier
    @Override
    public Basket validateBasket(Long basketId) {
        Optional<Basket> basket = basketRepository.findById(basketId);
        if (basket.isPresent()) {
            Basket updatedBasket = basket.get();
            updatedBasket.setDateValidation(java.time.LocalDate.now());
            updatedBasket.setStatut("validé");
            return basketRepository.save(updatedBasket);
        }
        throw new RuntimeException("Basket not found");
    }

    @Override
    public Basket clearBasket(Long basketId) {
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            basket.getProductIdsList().clear();
            basket.syncListToProductIds();
            basket.setDateModification(LocalDate.now());

            // Afficher la date avant de sauvegarder
            System.out.println("Date de modification avant sauvegarde : " + basket.getDateModification());

            // Sauvegarder et forcer la mise à jour avec flush()
            Basket updatedBasket = basketRepository.saveAndFlush(basket);

            // Afficher la date après la sauvegarde
            System.out.println("Date de modification après sauvegarde : " + updatedBasket.getDateModification());

            return updatedBasket;
        }
        return null;
    }




}