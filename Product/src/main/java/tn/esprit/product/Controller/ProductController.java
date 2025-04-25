package tn.esprit.product.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.product.Entity.Product;
import tn.esprit.product.Service.IProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final String UPLOAD_DIR = "Product/uploads/";
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("prix") Double prix,
            @RequestParam("image") MultipartFile image,
            @RequestParam("stock") Integer stock,
            @RequestParam("category") String category,
            @RequestParam("quantity") Integer quantity) {

        try {
            String imageUrl = saveImage(image);

            Product product = new Product();
            product.setNom(nom);
            product.setDescription(description);
            product.setPrix(prix);
            product.setImageUrl(imageUrl);
            product.setStock(stock);
            product.setCategory(category);
            product.setQuantity(quantity);

            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(201).body(savedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("prix") Double prix,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("stock") Integer stock,
            @RequestParam("category") String category,
            @RequestParam("quantity") Integer quantity) {

        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            product.setNom(nom);
            product.setDescription(description);
            product.setPrix(prix);
            product.setStock(stock);
            product.setCategory(category);
            product.setQuantity(quantity);

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                product.setImageUrl(imageUrl);
            }

            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/marketplace/{marketplaceId}")
    public ResponseEntity<Void> deleteAllByMarketplaceId(@PathVariable Long marketplaceId) {
        productService.deleteAllByMarketplaceId(marketplaceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{marketplaceId}/products")
    public List<Product> getProductsByMarketplaceId(@PathVariable("marketplaceId") Long marketplaceId) {
        return productService.getProductsByMarketplaceId(marketplaceId);
    }


    private String saveImage(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        Path uploadPath = Paths.get(System.getProperty("user.dir"), UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        image.transferTo(filePath.toFile());
        return fileName;
    }

    @PutMapping("/{id}/increase")
    public ResponseEntity<Product> increaseQuantity(@PathVariable Long id) {
        try {
            Product updated = productService.increaseQuantity(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/decrease")
    public ResponseEntity<Product> decreaseQuantity(@PathVariable Long id) {
        try {
            Product updated = productService.decreaseQuantity(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
