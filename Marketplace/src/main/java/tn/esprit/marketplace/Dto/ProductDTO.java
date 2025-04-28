package tn.esprit.marketplace.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

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

}