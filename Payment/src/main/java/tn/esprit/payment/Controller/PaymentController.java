package tn.esprit.payment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;
import tn.esprit.payment.Entity.Payment;
import tn.esprit.payment.Entity.PaymentRequest;
import tn.esprit.payment.Service.IPaymentService;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/create")
    public Map<String, Object> createPayment(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        BasketDTO basketDTO = paymentRequest.getBasketDTO();
        UserDTO userDTO = paymentRequest.getUserDTO();

        if (basketDTO == null || userDTO == null) {
            throw new IllegalArgumentException("BasketDTO or UserDTO is missing in the request.");
        }

        Long basketId = basketDTO.getId_Basket();
        Long userId = userDTO.getId_User();

        double amount = basketDTO.getTotal();

        // 1️⃣ Création du PaymentIntent Stripe
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (amount * 100)) // montant en centimes
                        .setCurrency("eur")
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // 2️⃣ Création du paiement dans la base
        Payment createdPayment = paymentService.createPayment(basketId, userId);
        createdPayment.setAmount(amount);
        createdPayment.setStatus("pending");
        createdPayment.setPaymentMethod("stripe");
        createdPayment.setPaymentDate(LocalDate.now());

        // Enregistre le paiement mis à jour
        paymentService.updatePayment(createdPayment.getId_Payment(), createdPayment);

        // 3️⃣ Validation panier
        paymentService.validateBasket(basketId);

        // 4️⃣ Réponse avec le clientSecret + infos internes
        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        response.put("paymentId", createdPayment.getId_Payment());
        response.put("status", createdPayment.getStatus());
        return response;
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

    @GetMapping("/history")
    public List<Payment> getPaymentHistory(
            @RequestParam(required = false) String status) {

        return paymentService.getPaymentHistoryByStatus(status);
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam String status) {
        paymentService.updatePaymentStatus(paymentId, status);
        return ResponseEntity.ok().build();
    }


}