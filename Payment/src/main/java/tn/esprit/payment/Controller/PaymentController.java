package tn.esprit.payment.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import tn.esprit.payment.Client.BasketClient;
import tn.esprit.payment.Client.UserClient;
import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;
import tn.esprit.payment.Entity.Payment;
import tn.esprit.payment.Entity.PaymentRequest;
import tn.esprit.payment.Service.IPaymentService;

import java.nio.charset.StandardCharsets;
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

    @Autowired
    private UserClient userClient;

    @Autowired
    private BasketClient basketClient;


    @PostMapping("/create")
    public Map<String, Object> createPayment(@RequestHeader("Authorization") String token) throws StripeException {
        Map<String, Object> response = new HashMap<>();

        try {
            // 1️⃣ Vérification et extraction de l'ID utilisateur à partir du token JWT
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Token d'autorisation manquant ou invalide.");
            }

            String jwt = token.replace("Bearer ", "");
            String SECRET_KEY = "bXlzdXBlcnNlY3JldGtleXdoaWNoaXMyNTZiaXRzbG9uZ2FuZHNhZmU=";

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Long userId = Long.parseLong(claims.get("user_id").toString());

            // 2️⃣ Récupération du User et du Basket via les Feign Clients
            UserDTO userDTO = userClient.getUserById(userId); // Utilisation du client Feign pour récupérer l'utilisateur
            if (userDTO == null) {
                throw new IllegalArgumentException("Utilisateur non trouvé.");
            }

            BasketDTO basketDTO = basketClient.getBasketByUser(userId); // Utilisation du client Feign pour récupérer le panier
            if (basketDTO == null) {
                throw new IllegalArgumentException("Aucun panier trouvé pour cet utilisateur.");
            }

            double amount = basketDTO.getTotal();

            // 3️⃣ Création du PaymentIntent Stripe
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // Montant en centimes
                    .setCurrency("eur")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // 4️⃣ Création du paiement dans la base de données
            Payment createdPayment = paymentService.createPayment(basketDTO.getId_Basket(), userId);
            createdPayment.setAmount(amount);
            createdPayment.setStatus("pending");
            createdPayment.setPaymentMethod("stripe");
            createdPayment.setPaymentDate(LocalDate.now());

            // Enregistrement du paiement mis à jour dans la base de données
            paymentService.updatePayment(createdPayment.getId_Payment(), createdPayment);

            // 5️⃣ Validation du panier
            paymentService.validateBasket(basketDTO.getId_Basket());

            // 6️⃣ Réponse avec le clientSecret + infos internes
            response.put("clientSecret", intent.getClientSecret());
            response.put("paymentId", createdPayment.getId_Payment());
            response.put("status", createdPayment.getStatus());
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
        } catch (StripeException e) {
            response.put("error", "Erreur lors de la création du PaymentIntent Stripe : " + e.getMessage());
        } catch (Exception e) {
            response.put("error", "Une erreur inattendue est survenue : " + e.getMessage());
        }

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