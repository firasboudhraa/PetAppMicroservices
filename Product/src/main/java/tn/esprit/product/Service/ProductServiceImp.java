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
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {


    private static final String UPLOAD_DIR = "Product/uploads/";
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
    public Product addProductWithImage(Product product, MultipartFile imageFile) throws IOException {
        String imageName = saveImage(imageFile);
        product.setImageUrl(UPLOAD_DIR + imageName);

        MarketplaceDto marketplace = marketplaceClient.getUniqueMarketplace();
        if (marketplace == null || marketplace.getId_Marketplace() == null) {
            throw new IllegalArgumentException("Invalid marketplace data received.");
        }
        product.setMarketplaceId(marketplace.getId_Marketplace());

        return productRepository.save(product);
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
    public Product updateProductWithImage(Long id, Product updatedProduct, MultipartFile imageFile) throws IOException {
        return productRepository.findById(id).map(product -> {
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Supprimer l'ancienne image si elle existe
                    if (product.getImageUrl() != null) {
                        Path oldImagePath = Paths.get(product.getImageUrl());
                        Files.deleteIfExists(oldImagePath);
                    }

                    // Sauvegarder la nouvelle image
                    String imageName = saveImage(imageFile);
                    product.setImageUrl(UPLOAD_DIR + imageName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to update image: " + e.getMessage());
                }
            }

            product.setNom(updatedProduct.getNom());
            product.setDescription(updatedProduct.getDescription());
            product.setPrix(updatedProduct.getPrix());
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

    public String saveImage(MultipartFile imageFile) throws IOException {
        // Créer le répertoire s'il n'existe pas
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Générer un nom de fichier unique
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Sauvegarder le fichier
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public void deleteAllByMarketplaceId(Long marketplaceId) {
        productRepository.deleteByMarketplaceId(marketplaceId);
    }

    @Override
    public Product increaseQuantity(Long id) {
        return productRepository.findById(id).map(product -> {
            if (product.getQuantity() < product.getStock()) {
                product.setQuantity(product.getQuantity() + 1);
                return productRepository.save(product);
            } else {
                throw new RuntimeException("Quantité maximale atteinte.");
            }
        }).orElseThrow(() -> new RuntimeException("Produit non trouvé."));
    }

    @Override
    public Product decreaseQuantity(Long id) {
        return productRepository.findById(id).map(product -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                return productRepository.save(product);
            } else {
                throw new RuntimeException("Quantité minimale atteinte.");
            }
        }).orElseThrow(() -> new RuntimeException("Produit non trouvé."));
    }

    @Override
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    // integration du user au crud

    @Override
    public Product addProductByUser(Long userId, Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = saveImage(imageFile);
            product.setImageUrl(UPLOAD_DIR + imageName);
        }

        product.setUserId(userId);

        MarketplaceDto marketplace = marketplaceClient.getUniqueMarketplace();
        if (marketplace == null || marketplace.getId_Marketplace() == null) {
            throw new IllegalArgumentException("Invalid marketplace data received.");
        }
        product.setMarketplaceId(marketplace.getId_Marketplace());

        return productRepository.save(product);
    }


    @Override
    public Product updateProductByUser(Long userId, Long productId, Product updatedProduct, MultipartFile imageFile) throws IOException {
        return productRepository.findById(productId).map(product -> {
            if (!product.getUserId().equals(userId)) {
                throw new RuntimeException("Unauthorized operation: You are not the owner of this product.");
            }

            product.setNom(updatedProduct.getNom());
            product.setDescription(updatedProduct.getDescription());
            product.setPrix(updatedProduct.getPrix());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());
            product.setQuantity(updatedProduct.getQuantity());

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    if (product.getImageUrl() != null) {
                        Path oldImagePath = Paths.get(product.getImageUrl());
                        Files.deleteIfExists(oldImagePath);
                    }
                    String imageName = saveImage(imageFile);
                    product.setImageUrl(UPLOAD_DIR + imageName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to update image: " + e.getMessage());
                }
            }

            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    @Override
    public void deleteProductByUser(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (!product.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized operation: You are not the owner of this product.");
        }

        productRepository.deleteById(productId);
    }


}
