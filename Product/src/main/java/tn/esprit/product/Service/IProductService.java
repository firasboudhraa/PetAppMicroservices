package tn.esprit.product.Service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.product.Entity.Product;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByMarketplaceId(Long marketplaceId);
    String saveImage(MultipartFile imageFile) throws IOException;
    Product addProductWithImage(Product product, MultipartFile imageFile) throws IOException;
    Product updateProductWithImage(Long id, Product updatedProduct, MultipartFile imageFile) throws IOException;
}
