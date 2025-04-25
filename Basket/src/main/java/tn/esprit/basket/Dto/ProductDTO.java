package tn.esprit.basket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

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
