package tn.esprit.basket.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}