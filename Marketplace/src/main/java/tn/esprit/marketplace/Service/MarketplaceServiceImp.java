package tn.esprit.marketplace.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.marketplace.Client.ProductClient;
import tn.esprit.marketplace.Dto.ProductDTO;
import tn.esprit.marketplace.Entity.Marketplace;
import tn.esprit.marketplace.Repository.MarketplaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MarketplaceServiceImp implements IMarketplaceService {

    private final MarketplaceRepository marketplaceRepository;
    private final ProductClient productClient;

    @Autowired
    public MarketplaceServiceImp(MarketplaceRepository marketplaceRepository, ProductClient productClient) {
        this.marketplaceRepository = marketplaceRepository;
        this.productClient = productClient;
    }

    @Override
    public List<Marketplace> getAllMarketplaces() {
        return marketplaceRepository.findAll();
    }

    @Override
    public Marketplace addMarketplace(Marketplace marketplace) {
        if (marketplaceRepository.count() >= 1) {
            throw new RuntimeException("Une marketplace existe déjà.");
        }
        return marketplaceRepository.save(marketplace);
    }

    @Override
    public Marketplace updateMarketplace(Long id, Marketplace updatedMarketplace) {
        Optional<Marketplace> optionalMarketplace = marketplaceRepository.findById(id);
        if (optionalMarketplace.isPresent()) {
            Marketplace m = optionalMarketplace.get();
            m.setName(updatedMarketplace.getName());
            m.setDescription(updatedMarketplace.getDescription());
            m.setStatut(updatedMarketplace.getStatut());
            return marketplaceRepository.save(m);
        } else {
            throw new RuntimeException("Marketplace not found");
        }
    }

    @Override
    public void deleteMarketplace(Long id) {
        marketplaceRepository.deleteById(id);
    }

    @Override
    public Marketplace getMarketplaceById(Long id) {
        return marketplaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marketplace not found"));
    }

    @Override
    public Marketplace getUniqueMarketplace() {
        return marketplaceRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Aucune marketplace disponible."));
    }

    @Override
    public List<ProductDTO> getProductsByMarketplaceId(Long marketplaceId) {
        return productClient.getProductsByMarketplaceId(marketplaceId);
    }


}