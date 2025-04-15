package tn.esprit.marketplace.Controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.marketplace.Dto.ProductDTO;
import tn.esprit.marketplace.Entity.Marketplace;
import tn.esprit.marketplace.Service.IMarketplaceService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/marketplaces")
public class MarketplaceController {

    private final IMarketplaceService marketplaceService;

    public MarketplaceController(IMarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @PostMapping
    public Marketplace addMarketplace(@RequestBody Marketplace marketplace) {
        return marketplaceService.addMarketplace(marketplace);
    }

    @PutMapping("/{id}")
    public Marketplace updateMarketplace(@PathVariable Long id, @RequestBody Marketplace marketplace) {
        return marketplaceService.updateMarketplace(id, marketplace);
    }

    @DeleteMapping("/{id}")
    public void deleteMarketplace(@PathVariable Long id) {
        marketplaceService.deleteMarketplace(id);
    }

    @GetMapping("/{id}")
    public Marketplace getMarketplace(@PathVariable Long id) {
        return marketplaceService.getMarketplaceById(id);
    }

    @GetMapping("/unique")
    public Marketplace getUniqueMarketplace() {
        List<Marketplace> marketplaces = marketplaceService.getAllMarketplaces();
        if (marketplaces.isEmpty()) {
            throw new RuntimeException("Aucune marketplace trouvée !");
        }
        return marketplaces.get(0);
    }

    @GetMapping("/{id}/products")
    public List<ProductDTO> getAllProductsFromMarketplace(@PathVariable("id") Long id) {
        return marketplaceService.getProductsByMarketplaceId(id);
    }



}
