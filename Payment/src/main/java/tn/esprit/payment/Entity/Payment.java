package tn.esprit.payment.Entity;


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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Payment;

    private Double amount;
    private String status;  // "pending", "completed", "failed"
    private String paymentMethod;
    private LocalDate paymentDate;
    private Long basketId;  // Référence au panier
    private Long userId;    // Référence à l'utilisateur

}