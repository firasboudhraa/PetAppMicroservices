package tn.esprit.product.Service;

import tn.esprit.product.Entity.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByMarketplaceId(Long marketplaceId);
}
