package tn.esprit.marketplace.Service;

import tn.esprit.marketplace.Dto.ProductDTO;
import tn.esprit.marketplace.Entity.Marketplace;

import java.util.List;

public interface IMarketplaceService {
    List<Marketplace> getAllMarketplaces();

    Marketplace addMarketplace(Marketplace marketplace);
    Marketplace updateMarketplace(Long id, Marketplace marketplace);
    void deleteMarketplace(Long id);
    Marketplace getMarketplaceById(Long id);
    Marketplace getUniqueMarketplace();

    List<ProductDTO> getProductsByMarketplaceId(Long marketplaceId);
}