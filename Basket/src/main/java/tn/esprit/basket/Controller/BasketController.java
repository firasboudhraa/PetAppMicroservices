package tn.esprit.basket.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.basket.Dto.ProductDTO;
import tn.esprit.basket.Entity.Basket;
import tn.esprit.basket.Repository.BasketRepository;
import tn.esprit.basket.Service.IBasketService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

    @Autowired
    private BasketRepository basketRepository;

    private final IBasketService basketService;

    // Créer un panier
    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        Basket createdBasket = basketService.createBasket(basket);
        return ResponseEntity.ok(createdBasket);
    }

    // Récupérer tous les paniers d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Basket>> getBasketsByUser(@PathVariable Long userId) {
        List<Basket> baskets = basketService.getBasketsByUserId(userId);
        return ResponseEntity.ok(baskets);
    }

    // Récupérer un panier par ID
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        Optional<Basket> basket = basketService.getBasketById(id);
        return basket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour un panier
    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable Long id, @RequestBody Basket basket) {
        Basket updatedBasket = basketService.updateBasket(id, basket);
        return updatedBasket != null ? ResponseEntity.ok(updatedBasket) : ResponseEntity.notFound().build();
    }

    // Supprimer un panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long id) {
        boolean isDeleted = basketService.deleteBasket(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{basketId}/validate")
    public ResponseEntity<Basket> validateBasket(@PathVariable Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        basket.setDateValidation(LocalDate.now());
        basket.setStatut("validé"); // ou "confirmé" si tu préfères
        Basket updatedBasket = basketRepository.save(basket);

        return ResponseEntity.ok(updatedBasket);
    }

    // Ajouter un produit au panier
    @PostMapping("/{basketId}/add-product/{productId}")
    public ResponseEntity<Basket> addProductToBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        Optional<Basket> basket = basketService.getBasketById(basketId);
        if (basket.isPresent()) {
            Basket updatedBasket = basketService.addProductToBasket(basketId, productId);
            return ResponseEntity.ok(updatedBasket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un produit du panier
    @PostMapping("/{basketId}/remove-product/{productId}")
    public ResponseEntity<Basket> removeProductFromBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        Optional<Basket> basket = basketService.getBasketById(basketId);
        if (basket.isPresent()) {
            Basket updatedBasket = basketService.removeProductFromBasket(basketId, productId);
            return ResponseEntity.ok(updatedBasket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/clear/{basketId}")
    public ResponseEntity<Basket> clearBasket(@PathVariable Long basketId) {
        Basket clearedBasket = basketService.clearBasket(basketId);
        if (clearedBasket != null) {
            return ResponseEntity.ok(clearedBasket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
