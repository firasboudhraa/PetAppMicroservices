package tn.esprit.basket.Service;

import tn.esprit.basket.Entity.Basket;

import java.util.List;
import java.util.Optional;

public interface IBasketService {

    Basket createBasket(Basket basket);
    List<Basket> getBasketsByUserId(Long userId);
    Optional<Basket> getBasketById(Long id);
    Basket updateBasket(Long id, Basket basket);
    boolean deleteBasket(Long id);

    List<Basket> getAllBaskets();

    Basket addProductToBasket(Long basketId, Long productId);
    Basket removeProductFromBasket(Long basketId, Long productId);
    Basket validateBasket(Long basketId);
    Basket clearBasket(Long basketId);
}
