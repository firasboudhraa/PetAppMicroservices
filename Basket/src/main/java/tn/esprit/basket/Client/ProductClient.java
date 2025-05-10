package tn.esprit.basket.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.basket.Dto.ProductDTO;

import java.util.List;

@FeignClient(name = "product-service", url = "${application.config.product.url}")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long productId);

    @GetMapping("/api/products/byBasket/{basketId}")
    List<ProductDTO> getProductsByBasketId(@PathVariable("basketId") Long basketId);

}

