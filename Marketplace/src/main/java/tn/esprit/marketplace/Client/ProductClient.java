package tn.esprit.marketplace.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.marketplace.Dto.ProductDTO;

import java.util.List;

@FeignClient(name = "product-service", url = "${application.config.product.url}")
public interface ProductClient {

    @GetMapping("/{marketplaceId}/products")
    List<ProductDTO> getProductsByMarketplaceId(@PathVariable("marketplaceId") Long marketplaceId);

    @DeleteMapping("/marketplace/{marketplaceId}")
    void deleteAllProductsByMarketplaceId(@PathVariable("marketplaceId") Long marketplaceId);
}

