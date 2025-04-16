package tn.esprit.product.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.product.Client.MarketplaceClient;
import tn.esprit.product.Dto.MarketplaceDto;
import tn.esprit.product.Entity.Product;
import tn.esprit.product.Repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {

    private final ProductRepository productRepository;
    private final MarketplaceClient marketplaceClient;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, MarketplaceClient marketplaceClient) {
        this.productRepository = productRepository;
        this.marketplaceClient = marketplaceClient;
    }

    @Override
    public Product addProduct(Product product) {
        try {
            MarketplaceDto marketplace = marketplaceClient.getUniqueMarketplace();
            if (marketplace == null || marketplace.getId_Marketplace() == null) {
                throw new IllegalArgumentException("Invalid marketplace data received.");
            }
            product.setMarketplaceId(marketplace.getId_Marketplace());
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching marketplace: " + e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
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
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
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
