package tn.esprit.product.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.product.Client.MarketplaceClient;
import tn.esprit.product.Dto.MarketplaceDto;
import tn.esprit.product.Entity.Product;
import tn.esprit.product.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private final ProductRepository productRepository;
    private final MarketplaceClient marketplaceClient;

    public ProductServiceImp(ProductRepository productRepository, MarketplaceClient marketplaceClient) {
        this.productRepository = productRepository;
        this.marketplaceClient = marketplaceClient;
    }


    @Override
    public Product addProduct(Product product) {
        MarketplaceDto marketplace;
        try {
            marketplace = marketplaceClient.getUniqueMarketplace();
            if (marketplace == null || marketplace.getId_Marketplace() == null) {
                throw new RuntimeException("Invalid marketplace data received.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching marketplace: " + e.getMessage());
        }

        product.setMarketplaceId(marketplace.getId_Marketplace());

        return productRepository.save(product);
    }


    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Mettre à jour les champs du produit
            product.setNom(updatedProduct.getNom());
            product.setDescription(updatedProduct.getDescription());
            product.setPrix(updatedProduct.getPrix());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setStock(updatedProduct.getStock());
            product.setLowStockThreshold(updatedProduct.getLowStockThreshold());
            product.setAlertSent(updatedProduct.getAlertSent());
            product.setCategory(updatedProduct.getCategory());
            product.setQuantity(updatedProduct.getQuantity());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByMarketplaceId(Long marketplaceId) {
        return productRepository.findByMarketplaceId(marketplaceId);
    }

}
