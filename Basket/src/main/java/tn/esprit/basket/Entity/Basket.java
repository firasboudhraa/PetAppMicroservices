package tn.esprit.basket.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Basket;

    private LocalDate dateCreation;
    private String statut; // en cours, validé, annulé
    private Double total;
    private String modePaiement; // carte, PayPal, etc.
    private LocalDate dateValidation;
    private LocalDate dateModification;

    private Long userId;
    private String productIds = "";

    @Transient
    private List<Long> productIdsList; // Liste des IDs de produits

    @PrePersist
    public void prePersist() {
        if (this.productIds == null) {
            this.productIds = "";
        }
        if (this.productIdsList == null) {
            this.productIdsList = new ArrayList<>();
        }
    }

}