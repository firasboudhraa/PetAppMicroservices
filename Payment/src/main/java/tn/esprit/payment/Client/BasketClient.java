package tn.esprit.payment.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import tn.esprit.payment.Dto.BasketDTO;

@FeignClient(name = "basket-service", url = "http://localhost:8013/api/baskets")
public interface BasketClient {

    @GetMapping("/user/{userId}")
    BasketDTO getBasketByUser(@PathVariable("userId") Long userId);

    @GetMapping("/{basketId}")
    BasketDTO getBasketById(@PathVariable("basketId") Long basketId);

    @PutMapping("/{basketId}/validate")
    BasketDTO validateBasket(@PathVariable("basketId") Long basketId);

    @PutMapping("/{basketId}/clear")
    void clearBasket(@PathVariable("basketId") Long basketId);


}