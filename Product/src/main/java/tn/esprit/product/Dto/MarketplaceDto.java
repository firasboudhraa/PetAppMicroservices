package tn.esprit.product.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketplaceDto {
    private Long id_Marketplace;
    private String name;
    private String description;
    private LocalDate dateCreation;
    private String statut;

}
