package tn.esprit.product.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Product;

    private String nom;
    private String description;
    private Double prix;
    private String imageUrl;
    private Integer stock;
    private Integer lowStockThreshold = 5;
    private Boolean alertSent = false;
    private String category;
    private Integer quantity;

    private Long marketplaceId;
}
