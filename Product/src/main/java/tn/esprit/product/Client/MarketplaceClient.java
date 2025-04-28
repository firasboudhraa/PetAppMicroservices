package tn.esprit.product.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tn.esprit.product.Dto.MarketplaceDto;

@FeignClient(name = "marketplace-service", url = "http://localhost:8016/api/marketplaces")
public interface MarketplaceClient {

    @GetMapping("/unique")
    MarketplaceDto getUniqueMarketplace();
}

