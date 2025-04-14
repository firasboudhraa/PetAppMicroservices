package tn.esprit.payment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;
import tn.esprit.payment.Entity.Payment;
import tn.esprit.payment.Entity.PaymentRequest;
import tn.esprit.payment.Service.IPaymentService;

import java.util.List;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    // Créer un paiement
    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest paymentRequest) {
        // Utilisation de basketDTO et userDTO à partir de PaymentRequest
        Long basketId = paymentRequest.getBasketDTO().getId_Basket();
        Long userId = paymentRequest.getUserDTO().getId_User();

        // Création du paiement en passant les ID
        return paymentService.createPayment(basketId, userId);
    }

    // Lire un paiement par ID
    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }

    // Mettre à jour un paiement
    @PutMapping("/update/{paymentId}")
    public Payment updatePayment(@PathVariable Long paymentId, @RequestBody Payment payment) {
        return paymentService.updatePayment(paymentId, payment);
    }

    // Supprimer un paiement
    @DeleteMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return "Payment with ID " + paymentId + " has been deleted.";
    }

    // Lister tous les paiements
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
