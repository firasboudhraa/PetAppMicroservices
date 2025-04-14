package tn.esprit.payment.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {

    private Long id_Basket;
    private LocalDate dateCreation;
    private String statut; // en cours, validé, annulé
    private Double total;
    private String modePaiement; // carte, PayPal, etc.
    private LocalDate dateValidation;
    private LocalDate dateModification;
    private Long userId;
}